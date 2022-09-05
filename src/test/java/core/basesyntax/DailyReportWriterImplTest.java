package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.DailyReportWriter;
import core.basesyntax.service.impl.DailyReportWriterImpl;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class DailyReportWriterImplTest {
    private final String toFilePath = "src/test/resources/test-report.csv";
    private String report;
    private DailyReportWriter reportWriter;

    @Before
    public void setUp() {
        reportWriter = new DailyReportWriterImpl();
        report = "fruit,quantity"
                + System.lineSeparator()
                + "apple,40"
                + System.lineSeparator()
                + "orange,60"
                + System.lineSeparator()
                + "banana,1";
    }

    @Test
    public void fileExists_0k() {
        reportWriter.write(report, toFilePath);
        File file = new File(toFilePath);
        boolean exists = file.exists();
        assertTrue(exists);
    }

    @Test
    public void fileAndReportSameLength_Ok() {
        reportWriter.write(report, toFilePath);
        File file = new File(toFilePath);
        boolean same = file.length() == report.length();
        assertTrue(same);
    }

    @Test
    public void cannotWriteToFile_NotOk() {
        try {
            reportWriter.write(report, "");
        } catch (RuntimeException e) {
            System.out.println("Exception: " + e);
        }
    }
}
