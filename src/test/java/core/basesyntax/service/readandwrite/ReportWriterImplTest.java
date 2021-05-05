package core.basesyntax.service.readandwrite;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/reportTest.csv";
    private static final String WRONG_PATH_TO_FILE = "src/test/resoces/reportTest.csv";
    private static final String CONTENT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static ReportWriter reportWriter;

    @BeforeClass
    public static void beforeClass() {
        reportWriter = new ReportWriterImpl();
    }

    @Test
    public void writeReport_Ok() {
        boolean expected = true;
        boolean actual = reportWriter.writeReport(PATH_TO_FILE, CONTENT);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeReport_NotOk() {
        reportWriter.writeReport(WRONG_PATH_TO_FILE, CONTENT);
    }
}
