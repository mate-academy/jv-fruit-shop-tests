package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReaderService;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String INPUT = "src/main/resources/output.csv";
    private static final String INVALID_PATH = "/dev/null";

    @BeforeAll
    static void clear() {
        Storage.storage.clear();
    }

    @AfterEach
    void clearAfter() {
        Storage.storage.clear();
    }

    @Test
    void when_FileNotExists_NotOk() {
        ReaderService readerService = new ReaderServiceImpl();
        assertThrows(RuntimeException.class,
                () -> readerService.readOperations(INVALID_PATH));
    }

    @Test
    void when_FileValid_Ok() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "p,apple,15");
        try (FileWriter fileWriter = new FileWriter(INPUT)) {
            for (String line : lines) {
                fileWriter.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> expected = lines.stream().skip(1).toList();
        ReaderService readerService = new ReaderServiceImpl();
        List<String> actual = readerService.readOperations(INPUT);
        assertEquals(expected, actual);
    }
}
