package core.basesyntax.file.reader;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileReaderImplTest {
    private static final String FILE_PATH_OK = "src/test/resources/testFilePositive.csv";
    private static final String FILE_PATH_WRONG = "src/test/resources/Wrong.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readTest_Ok() {
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(FILE_PATH_OK));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file " + FILE_PATH_OK);
        }
        List<String> actual = fileReader.read(FILE_PATH_OK);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readTest_Not_Ok() {
        fileReader.read(FILE_PATH_WRONG);
    }
}
