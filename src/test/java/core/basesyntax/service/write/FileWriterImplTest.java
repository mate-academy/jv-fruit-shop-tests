package core.basesyntax.service.write;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String CORRECT_PATH = "src/test/resources/report_test.csv";
    private static final String INCORRECT_PATH = "";
    private static FileWriter fileWriter;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void white_correctPath_Ok() {
        Assert.assertTrue(fileWriter.write("", CORRECT_PATH));
    }

    @Test
    public void write_incorrectPath_notOk() {
        Assert.assertThrows(RuntimeException.class, () -> fileWriter.write("", INCORRECT_PATH));
    }
}
