package core.basesyntax.service;

import core.basesyntax.service.impl.ReportWriterServiceImpl;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportWriterServiceTest {
    private static final String READER_FAILURE_MESSAGE = "Cannot read from file {%s}!";
    private static final String REPORT_FILE_NAME = "src/main/resources/report.csv";
    private static final String EMPTY_STRING = "";
    private static final int READ_END_INDEX = -1;
    private static ReportWriterService reportWriterService;

    @BeforeAll
    public static void setUp() {
        reportWriterService = new ReportWriterServiceImpl();
    }

    @Test
    public void write_correctInputData_Ok() {
        String report = """
                fruit,quantity
                banana,152
                apple,90
                """;
        reportWriterService.writeReport(report, REPORT_FILE_NAME);

        String actual = readReportFile();
        Assertions.assertEquals(report, actual);
    }

    @Test
    public void write_nullReportData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> reportWriterService.writeReport(null, REPORT_FILE_NAME));
    }

    @Test
    public void write_nullReportFileName_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> reportWriterService.writeReport(EMPTY_STRING, null));
    }

    @Test
    public void write_nullInputFields_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> reportWriterService.writeReport(null, null));
    }

    @Test
    public void write_emptyInputFields_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> reportWriterService.writeReport(EMPTY_STRING, EMPTY_STRING));
    }

    @Test
    public void write_emptyReportInput_Ok() {
        reportWriterService.writeReport(EMPTY_STRING, REPORT_FILE_NAME);
        Assertions.assertEquals(EMPTY_STRING, readReportFile());
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
