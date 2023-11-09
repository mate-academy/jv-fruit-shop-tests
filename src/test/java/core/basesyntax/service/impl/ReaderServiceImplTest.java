package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/input.csv";
    private static final String INVALID_FILE_PATH = "non_existent_file.txt";
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
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
        String filePath = "src/test/resources/input.csv";
        List<String> data = readerService.read(filePath);
        assertEquals(8, data.size());
        assertFalse(data.get(0).startsWith("type,fruit,quantity"));
    }
}
