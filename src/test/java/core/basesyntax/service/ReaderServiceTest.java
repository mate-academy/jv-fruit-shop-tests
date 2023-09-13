package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String VALID_FILE_PATH = "src/test/resources/valid_data.csv";
    private static final String INVALID_FILE_PATH = "test/resources/valid_data.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty_file.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_validFile_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,10",
                "p,banana,13", "r,apple,10"
        );
        List<String> actual = readerService.readFromFile(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void readData_emptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = readerService.readFromFile(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void readData_invalidFilePath_NotOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(INVALID_FILE_PATH));
    }
}
