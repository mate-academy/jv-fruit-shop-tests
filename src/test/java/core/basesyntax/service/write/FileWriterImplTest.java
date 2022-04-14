package core.basesyntax.service.write;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Assert.assertTrue(fileWriter.write("b,banana,100", CORRECT_PATH));
        String expected = "b,banana,100";
        String actual;
        try {
            actual = Files.readString(Path.of(CORRECT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't reading file");
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readerFile() {
    }

    @Test
    public void write_incorrectPath_notOk() {
        Assert.assertThrows(RuntimeException.class, () -> fileWriter.write("", INCORRECT_PATH));
    }
}
