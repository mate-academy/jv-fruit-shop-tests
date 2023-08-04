package core.basesyntax.service.implemantation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportWriter;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private static final String REPORT_TEXT = "fruit,quantity\nbanana,152";
    private static final String CORRECT_PATH_FILE =
            "src/test/resources/write_test/correct_report.csv";
    private static final String DEFAULT_PATH_DIRECTORY = "src/test/resources/write_test/";
    private ReportWriter reportWriter;

    @BeforeEach
    void setUp() {
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
}
