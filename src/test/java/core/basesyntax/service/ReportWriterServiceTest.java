package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.ReportWriterServiceImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportWriterServiceTest {
    private static final String READER_FAILURE_MESSAGE = "Cannot read from file {%s}!";
    private static final String REPORT_FILE_NAME = "src/main/resources/report.csv";
    private static final String EMPTY_STRING = "";
    private static final int READ_END_INDEX = -1;
    private static ReportWriterService reportWriterService;

    @BeforeAll
    static void setUp() {
        reportWriterService = new ReportWriterServiceImpl();
    }

    @Test
    void write_correctInputData_Ok() {
        String report = """
                fruit,quantity
                banana,152
                apple,90
                """;
        reportWriterService.writeReport(report, REPORT_FILE_NAME);
        String actual = readReportFile();
        assertEquals(report, actual);
        boolean isDeleted = new File(REPORT_FILE_NAME).delete();
        assertTrue(isDeleted);
    }

    @Test
    void write_nullReportData_notOk() {
        assertThrows(RuntimeException.class,
                () -> reportWriterService.writeReport(null, REPORT_FILE_NAME));
    }

    @Test
    void write_nullReportFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> reportWriterService.writeReport(EMPTY_STRING, null));
    }

    @Test
    void write_nullInputFields_notOk() {
        assertThrows(RuntimeException.class,
                () -> reportWriterService.writeReport(null, null));
    }

    @Test
    void write_emptyInputFields_notOk() {
        assertThrows(RuntimeException.class,
                () -> reportWriterService.writeReport(EMPTY_STRING, EMPTY_STRING));
    }

    @Test
    void write_emptyReportInput_Ok() {
        reportWriterService.writeReport(EMPTY_STRING, REPORT_FILE_NAME);
        assertEquals(EMPTY_STRING, readReportFile());
        boolean isDeleted = new File(REPORT_FILE_NAME).delete();
        assertTrue(isDeleted);
    }

    private String readReportFile() {
        StringBuilder reportInputBuilder = new StringBuilder();
        try (FileInputStream dataStream = new FileInputStream(REPORT_FILE_NAME)) {
            int charInAscii;
            while ((charInAscii = dataStream.read()) != READ_END_INDEX) {
                reportInputBuilder.append((char) charInAscii);
            }
        } catch (IOException ex) {
            throw new RuntimeException(String
                    .format(READER_FAILURE_MESSAGE, REPORT_FILE_NAME), ex);
        }
        return reportInputBuilder.toString();
    }
}
