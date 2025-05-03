package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.CsvWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String EMPTY_FILE = "src/test/resources/emptyTest.csv";
    private static final String VALID_FILE = "src/test/resources/validInputTest.csv";
    private static final String INVALID_PATH = "invalid/path.csv";
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new CsvWriterServiceImpl();
    }

    @Test
    void write_emptyReport_Ok() {
        writerService.write(EMPTY_FILE, "");
        assertEquals("", readFile(EMPTY_FILE));
    }

    @Test
    void write_validReport_Ok() {
        writerService.write(VALID_FILE, "some text");
        assertEquals("some text", readFile(VALID_FILE));
    }

    @Test
    void write_invalidPath_Ok() {
        assertThrows(RuntimeException.class, () -> writerService.write(INVALID_PATH, "text"));
    }

    @Test
    void write_null_NotOk() {
        assertThrows(RuntimeException.class, () -> writerService.write(null, "text"));
    }

    private static String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + path);
        }
    }
}
