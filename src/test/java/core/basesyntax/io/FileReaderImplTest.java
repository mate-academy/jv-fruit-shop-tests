package core.basesyntax.io;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String CORRECT_PATH_TO_FILE = "src/test/resources/InputFile.csv";
    private static final String INCORRECT_PATH_TO_FILE = "src/test/resources/OutputFile.csv";
    private static final String EMPTY_PATH_TO_FILE = "";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/EmptyInputFile.csv";
    private FileReader fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_correctFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReader.readFromFile(CORRECT_PATH_TO_FILE);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_incorrectFile_notOk() {
        fileReader.readFromFile(INCORRECT_PATH_TO_FILE);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_emptyPathToFile_notOk() {
        fileReader.readFromFile(EMPTY_PATH_TO_FILE);
    }

    @Test
    public void readFromFile_EmptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.readFromFile(PATH_TO_EMPTY_FILE);
        assertEquals(expected, actual);
    }
}
