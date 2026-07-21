package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() {
        List<String> actual = fileReader.read("src/main/resources/input.csv");
        assertFalse(actual.isEmpty(), "File should contain lines.");
        assertEquals("type,fruit,quantity", actual.get(0), "Header line should match.");
    }

    @Test
    void read_emptyFile_ok() {
        List<String> actual = fileReader.read("src/test/resources/empty.csv");
        assertTrue(actual.isEmpty(), "Result list should be empty when reading an empty file.");
    }

    @Test
    void read_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read("invalid/path/to/file.csv");
        }, "Should throw RuntimeException if file doesn't exist.");
    }
}
