package core.basesyntax.data.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.exeption.UnknownOperationException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String TEST_FILE_NAME = "src/reports/report";
    private static final String NON_EXISTENT_FILE_NAME = "src/reports/nonExistent.csv";
    private static final String EMPTY_FILE_NAME = "src/reports/empty.csv";
    private static final List<String> EMPTY_LIST = new ArrayList<>();
    private static List<String> EXPECTED;
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
        EXPECTED = List.of("fruit,quantity", "banana,130", "apple,150");
    }

    @Test
    void read_fromExistentFile_Ok() {
        List<String> actual = fileReader.readFile(TEST_FILE_NAME);
        assertEquals(EXPECTED, actual);
    }

    @Test
    void read_fromNonExistentFile_NotOk() {
        assertThrows(UnknownOperationException.class,
                () -> fileReader.readFile(NON_EXISTENT_FILE_NAME),
                "Can't read data from file name: "
                        + NON_EXISTENT_FILE_NAME);

    }

    @Test
    void read_fromEmptyFile_Ok() {
        List<String> actual = fileReader.readFile(EMPTY_FILE_NAME);
        assertEquals(EMPTY_LIST, actual);
    }
}
