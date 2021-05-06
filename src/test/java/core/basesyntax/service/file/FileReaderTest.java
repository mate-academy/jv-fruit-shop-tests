package core.basesyntax.service.file;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    public static final String FILE_TO_READ = "src/test/resources/readTest.csv";
    private static FileReader fileReader;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_readCorrectFile_Ok() {
        List<String> actual = fileReader.readFile(FILE_TO_READ);
        expected = List.of("I", "hate", "Junit4");
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_readEmptyFile_Ok() {
        List<String> actual = fileReader.readFile("src/test/resources/emptyFile.txt");
        expected = List.of();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_readNonExistentFile_NotOk() {
        List<String> actual = fileReader.readFile("src/test/resources/clown.txt");
    }
}
