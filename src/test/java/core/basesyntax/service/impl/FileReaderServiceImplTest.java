package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderServiceImplTest {
    private final FileReaderServiceImpl service = new FileReaderServiceImpl();

    @Test
    void read_validFile_shouldSkipHeader(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("test.csv");
        Files.write(file, List.of(
                "type,fruit,quantity",
                "b,banana,200",
                "s,apple,100"));
        List<String> result = service.read(file.toString());
        assertEquals(2, result.size());
        assertEquals("b,banana,200", result.get(0));
        assertEquals("s,apple,100", result.get(1));
    }

    @Test
    void read_fileWithOnlyHeader_returnsEmptyList(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("empty.csv");
        Files.write(file, List.of("type,fruit,quantity"));
        List<String> result = service.read(file.toString());
        assertTrue(result.isEmpty());
    }

    @Test
    void errorWhenReadingNonexistentFile() {
        assertThrows(RuntimeException.class,
                () -> service.read("not_existing_file.csv"));
    }

    @Test
    void read_fileWithEmptyLines_shouldIgnoreEmptyLines(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("test.csv");
        Files.write(file, List.of(
                "type,fruit,quantity",
                "",
                "b,banana,200",
                "",
                "s,apple,100"));
        List<String> result = service.read(file.toString());
        assertEquals(2, result.size());
    }

    @Test
    void read_emptyFile_returnsEmptyList(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("empty.csv");
        Files.write(file, List.of());
        List<String> result = service.read(file.toString());
        assertTrue(result.isEmpty());
    }

    @Test
    void read_nullInput_shouldReturnEmptyList() {
        List<String> result = service.read(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
