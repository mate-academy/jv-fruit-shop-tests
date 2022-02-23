package core.fruitshop.service.impl;

import static org.junit.Assert.assertEquals;

import core.fruitshop.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String FILE_PATH = "src/test/java/resources/FileReaderTestFile";
    private static final String EMPTY_FILE_PATH = "src/test/java/resources/EmptyFile";
    private static final String INVALID_PATH = "NO|SUCH|FILE";
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    public void readFromFile_validFile_ok() {
        List<String> expected = List.of("b,banana,50", "b,apple,100", "s,apple,100", "p,banana,30");
        List<String> actual = fileReader.readFromFile(FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.readFromFile(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_noSuchFile_notOk() {
        fileReader.readFromFile(INVALID_PATH);
    }
}


