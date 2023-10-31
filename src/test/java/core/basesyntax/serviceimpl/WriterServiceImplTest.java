package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.WriterService;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String OUTPUT = "src/test/resources/output.csv";
    private static final String INVALID_PATH = "dev/null";
    private static WriterService writerService;

    @BeforeAll
    static void init() {
        writerService = new WriterServiceImpl();
    }

    @AfterEach
    void clearAfter() {
        Storage.storage.clear();
    }

    @Test
    void is_fileExists_Ok() {
        writerService.writeAll(OUTPUT);
        File file = new File(OUTPUT);
        assertTrue(file.exists());
    }

    @Test
    void when_pathIsInvalid_notOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeAll(INVALID_PATH));
    }

    @Test
    void is_ContextWright_Ok() {
        Storage.storage.put("banana", 100);
        Storage.storage.put("apple", 50);
        List<String> expected = List.of(
                "banana,100",
                "apple,50"
        );
        writerService.writeAll(OUTPUT);
        ReaderService readerService = new ReaderServiceImpl();
        List<String> actual = readerService.readOperations(OUTPUT);
        assertEquals(expected, actual);
    }
}
