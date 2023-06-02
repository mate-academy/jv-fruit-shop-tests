package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeAll
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_file_ok() {
        List<String> expected;
        List<String> actual;
        try {
            expected = Files.readAllLines(Path.of("src/test/resources/valid_input.csv"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        actual = readerService.readFromFile("src/test/resources/valid_input.csv");
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_emptyFile_notOk() {
        assertThrows(RuntimeException.class, ()
                -> readerService.readFromFile("src/test/resources/empty_input.csv"));
    }

    @Test
    public void readFromFile_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, ()
                -> readerService.readFromFile("path/to/nonexistent/file.csv"));
    }
}
