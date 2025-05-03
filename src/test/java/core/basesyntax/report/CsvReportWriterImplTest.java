package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class CsvReportWriterImplTest {
    private static final String REPORT_PATH = "src/main/resources/report.csv";
    private static final String TEST_REPORT_PATH = "src/main/resources/test_report.csv";

    @Test
    void reportToFile_ok() throws IOException {
        ReportWriter reportWriter = new CsvReportWriterImpl();
        String expected = "fruit,quantity\n"
                + "mango,300\n"
                + "banana,200\n"
                + "orange,500";
        reportWriter.writeReport(expected, REPORT_PATH);
        String actual = Files.readString(Paths.get(TEST_REPORT_PATH), StandardCharsets.UTF_8);
        assertEquals(expected, actual);
    }

    @Test
    void reportToFileNotExist_notOk() {
        ReportWriter reportWriter = new CsvReportWriterImpl();
        assertThrows(NullPointerException.class, ()
                -> reportWriter.writeReport("test_report", null));
    }
}
