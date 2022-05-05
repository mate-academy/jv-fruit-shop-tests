package core.basesyntax.servise.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriterImplTest {
    private static final String FILE_PATH = "src/test/resources/report.csv";
    private Writer writer;

    @Before
    public void setUp() {
        writer = new WriterImpl();
    }

    @Test
    public void writeToFile_ok() {
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "orange,32"
                + System.lineSeparator()
                + "apple,90";
        writer.writeToFile(FILE_PATH, expected);
        String actual = readFromFile(FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_NotOk() {
        writer.writeToFile("", "some string");
    }

    private String readFromFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file" + FILE_PATH, e);
        }
    }
}
