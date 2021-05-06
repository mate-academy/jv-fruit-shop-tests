package core.basesyntax.files;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class FileReaderImplTest {
    private static final String FILE_PATH = "src/test/java/resources/test_shop.csv";
    private static final String WRONG_FILE_PATH = "src/test/java/resources/wrong_test.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_TestWithCorrectPath_Ok() {
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("We can't read from file");
        }
        List<String> actual = fileReader.readFromFile(FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_TestWithIncorrectPath_NotOk() {
        fileReader.readFromFile(WRONG_FILE_PATH);
    }
}