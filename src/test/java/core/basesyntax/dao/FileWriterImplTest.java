package core.basesyntax.dao;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    public static final String CORRECT_PATH = "src/test/java/resources/output_test.csv";
    public static final String INCORRECT_PATH = "";
    private static FileWriterImpl writerService;

    @BeforeClass
    public static void setUp() {
        FileWriter fileWriter = new FileWriterImpl();
    }

    @Test
    public void write_correctPath_ok() {
        FileWriter fileWriter = new FileWriterImpl();
        Assert.assertTrue("File is written!", fileWriter.write(CORRECT_PATH, ""));
    }

    @Test
    public void write_incorrectPath_notOk() {
        Assert.assertThrows("Exception should be thrown",
                RuntimeException.class, () -> writerService.write(INCORRECT_PATH, ""));
    }
}
