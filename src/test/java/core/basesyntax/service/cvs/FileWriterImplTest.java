package core.basesyntax.service.cvs;

import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String FILE_REPORT = "src/test/resources/fruit-shop-report-test.csv";
    private static final String DATA_REPORT = "fruit,quantity" + System.lineSeparator() + "apple,25"
            + System.lineSeparator() + "orange,25" + System.lineSeparator() + "melon,35";
    private FileWriter fileWriter;

    @Before
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_Ok() {
        fileWriter.writeToFile(FILE_REPORT, DATA_REPORT);
        assertTrue(new File(FILE_REPORT).exists());
        assertTrue(new File(FILE_REPORT).canRead());
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_NotOk() {
        fileWriter.writeToFile("", DATA_REPORT);
    }
}
