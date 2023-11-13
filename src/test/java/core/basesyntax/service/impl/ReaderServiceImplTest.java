package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/input.csv";
    private static final String INVALID_FILE_PATH = "non_existent_file.txt";
    private static final int NUMBER_OF_LINES = 8;
    private static final int FIRST_LINE = 0;
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
     void read_allFromFilePath_ok() {
        List<String> data = readerService.read(FILE_PATH);
        assertFalse(data.isEmpty());
    }

    @Test
    void read_invalidFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.read(INVALID_FILE_PATH));
    }

    @Test
    void read_nullFilePath_notOk() {
        assertThrows(IllegalArgumentException.class, () -> readerService.read(null));
    }

    @Test
    void read_skipFirstLine_ok() {
        List<String> data = readerService.read(FILE_PATH);
        assertEquals(NUMBER_OF_LINES, data.size());
        assertFalse(data.get(FIRST_LINE).startsWith("type,fruit,quantity"));
    }
}
