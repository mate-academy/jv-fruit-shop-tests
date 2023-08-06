package core.basesyntax.service.implemantation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReportWriter;
import java.io.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private static final String REPORT_TEXT = "fruit,quantity\nbanana,152";
    private static final String CORRECT_PATH_FILE =
            "src/test/resources/write_test/correct_report.csv";
    private static final String INCORRECT_FILE_NAME = null;
    private static final String DEFAULT_PATH_DIRECTORY = "src/test/resources/write_test/";
    private static final String EXPECTED_ERROR_MESSAGE_FILE_NAME_NULL =
            "File name can`t be a null";
    private static ReportWriter reportWriter;

    @BeforeAll
    static void beforeAll() {
        reportWriter = new ReportWriterImpl();
    }

    @Test
    void writerReport_Ok() {
        File defaultDirectory = new File(DEFAULT_PATH_DIRECTORY);
        int initialNumberOfFiles = defaultDirectory.list().length;

        reportWriter.writeReport(REPORT_TEXT, CORRECT_PATH_FILE);

        int expected = initialNumberOfFiles + 1;
        int actual = defaultDirectory.list().length;

        for (File file : defaultDirectory.listFiles()) {
            file.delete();
        }

        assertEquals(expected, actual);
    }

    @Test
    void writer_fileNameNull_notOk() {
        var actual = assertThrows(RuntimeException.class,
                () -> reportWriter.writeReport(REPORT_TEXT, INCORRECT_FILE_NAME));

        assertEquals(EXPECTED_ERROR_MESSAGE_FILE_NAME_NULL, actual.getMessage());
    }
}
