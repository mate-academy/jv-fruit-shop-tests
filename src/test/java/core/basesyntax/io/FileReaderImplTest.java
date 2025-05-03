package core.basesyntax.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String FILE_PATH = "src/test/resources/file.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty_file.csv";
    private List<String> expected;

    @BeforeEach
    public void setUp() {
        expected = new ArrayList<>();
    }

    @Test
    void readTransactionsFromFile_ok() {
        expected = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.readFile(FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void readEmptyFileGetEmptyList_ok() {
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.readFile(EMPTY_FILE_PATH);
        assertEquals(expected, actual, "Expected an empty list when reading from an empty file.");
    }

    @Test
    void readFileNotExist_notOk() {
        FileReader fileReader = new FileReaderImpl();
        assertThrows(RuntimeException.class, () -> fileReader.readFile("wrong_path"),
                "Expected RuntimeException when reading from an non-existing file.");
    }
}
