package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReportWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriteReportToFileTest {
    private static final String REPORT_FILE_CSV = "src/test/java/resources/testReport.csv";
    private static ReportWriter reportWriter;

    @BeforeAll
    public static void initialize() {
        reportWriter = new WriteReportToFile();
    }

    @Test
    public void write_writingInformationToFile_Ok() {
        String fruitReport = "banana,160";
        boolean actual = reportWriter.write(REPORT_FILE_CSV, fruitReport);
        assertTrue(actual);
    }

    @Test
    public void write_writingInformation_notOk() {
        assertThrows(RuntimeException.class,
                () -> reportWriter.write(REPORT_FILE_CSV, null));
    }
}
