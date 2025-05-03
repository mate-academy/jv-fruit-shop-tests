package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String VALID_FIlE_PATH = "src/test/resources/test-valid-input.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/blablabla.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readerService_validFilePath_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertIterableEquals(expected, readerService.readFromFile(VALID_FIlE_PATH));
    }

    @Test
    void readerService_invalidFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(INVALID_FILE_PATH));
    }
}
