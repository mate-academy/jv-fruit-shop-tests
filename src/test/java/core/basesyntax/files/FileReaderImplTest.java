package core.basesyntax.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/valid.csv";
    private static final String INVALID_PATH = "src/test/resources/invalid.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";
    private static FileReader fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_readFromExistingFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReader.read(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_readFromDefunctFile_NotOk() {
        fileReader.read(INVALID_PATH);
    }

    @Test
    public void read_readFromEmptyFile_Ok() {
        List<String> actual = fileReader.read(EMPTY_FILE_PATH);
        assertTrue(actual.isEmpty());
    }
}
