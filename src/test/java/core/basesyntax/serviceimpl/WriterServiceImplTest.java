package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.WriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String OUTPUT = "src/test/resources/output.csv";
    private static final String INVALID_PATH = "dev/null";

    @BeforeAll
    static void clear() {
        Storage.storage.clear();
    }

    @AfterEach
    void clearAfter() {
        Storage.storage.clear();
    }

    @Test
    void is_fileExists_Ok() {
        WriterService writerService = new WriterServiceImpl();
        writerService.writeAll(OUTPUT);
        File file = new File(OUTPUT);
        assertTrue(file.exists());
    }

    @Test
    void when_pathIsInvalid_notOk() {
        WriterService writerService = new WriterServiceImpl();
        assertThrows(RuntimeException.class, () -> writerService.writeAll(INVALID_PATH));
    }

    @Test
    void is_ContextWright_Ok() {
        Storage.storage.put("banana", 100);
        Storage.storage.put("apple", 50);
        List<String> expected = List.of(
                HEADER,
                "banana,100",
                "apple,50"
        );
        WriterService writerService = new WriterServiceImpl();
        writerService.writeAll(OUTPUT);
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT))) {
            List<String> actual = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                actual.add(line);
            }
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
