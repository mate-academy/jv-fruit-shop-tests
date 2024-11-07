package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exception.FileException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String TEST_FILE_NAME = "src/main/resources/test.csv";
    private static final String NON_EXISTENT_FILE_NAME = "src/main/resources/nonExistent.csv";
    private static final String EMPTY_FILE_NAME = "src/main/resources/empty.csv";
    private static final List<String> EMPTY_LIST = new ArrayList<>();
    private static List<String> EXPECTED;
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
        EXPECTED = List.of("fruit,quantity", "banana,152", "apple,90");
    }

    @Test
    void read_fromExistentFile_Ok() {
        List<String> actual = fileReader.read(TEST_FILE_NAME);
        assertEquals(EXPECTED, actual);
    }

    @Test
    void read_fromNonExistentFile_NotOk() {
        assertThrows(FileException.class,
                () -> fileReader.read(NON_EXISTENT_FILE_NAME),
                "Can't read data from file name: "
                        + NON_EXISTENT_FILE_NAME);

    }

    @Test
    void read_fromEmptyFile_Ok() {
        List<String> actual = fileReader.read(EMPTY_FILE_NAME);
        assertEquals(EMPTY_LIST, actual);
    }
}
