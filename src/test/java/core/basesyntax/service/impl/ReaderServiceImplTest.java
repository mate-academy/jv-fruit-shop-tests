package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final String CORRECT_FILE_PATH = "src/test/resources/okDataFile.csv";
    private static final String NON_EXISTENT_FILE_PATH = "src/test/resources/nonExistent.csv";

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readCorrectDataFile_Ok() {
        List<String> actualStrings = readerService.readFile(CORRECT_FILE_PATH);
        List<String> expectedStrings = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(expectedStrings, actualStrings);
    }

    @Test
    void readEmptyFile_Ok() {
        List<String> actualStrings = readerService.readFile(EMPTY_FILE_PATH);
        List<String> expectedStrings = new ArrayList<>();
        assertEquals(expectedStrings, actualStrings);
    }

    @Test
    void readNonExistentFile_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            readerService.readFile(NON_EXISTENT_FILE_PATH);
        });
    }
}
