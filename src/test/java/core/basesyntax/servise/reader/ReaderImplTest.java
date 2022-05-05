package core.basesyntax.servise.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderImplTest {
    private static final String PATH_INPUT_FILE = "src/test/resources/inputData.csv";
    private Reader reader;

    @Before
    public void setUp() {
        reader = new ReaderImpl();
    }

    @Test
    public void readFromFile_ok() {
        List<String> expected = readFromFile(PATH_INPUT_FILE);
        List<String> actual = reader.readFromFile(PATH_INPUT_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotOk() {
        reader.readFromFile("");
    }

    private List<String> readFromFile(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read all line" + path, e);
        }
    }
}
