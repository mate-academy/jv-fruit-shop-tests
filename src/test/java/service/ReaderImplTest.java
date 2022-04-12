package service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String FIRST_INPUT_PATH = "src/test/resources/testInput.csv";
    private static final String EMPTY_INPUT_PATH = "src/test/resources/emptyInput.csv";
    private static final String NON_EXISTING_FILE_PATH = "src/test/resources/inputNONExisting.csv";
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderImpl();
    }

    @Test
    public void readFromFile_readFromFirstInput_Ok() {
        List<String> expected = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = reader.read(FIRST_INPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_readFromEmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = reader.read(EMPTY_INPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_readFromNullPathFile_NotOk() {
        reader.read(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_readFromNonExistingPathFife_NotOk() {
        reader.read(NON_EXISTING_FILE_PATH);
    }
}
