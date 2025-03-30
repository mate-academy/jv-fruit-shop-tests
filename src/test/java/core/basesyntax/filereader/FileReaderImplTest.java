package core.basesyntax.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {
    @TempDir
    private Path tempDir;

    private final FileReaderImpl fileReader = new FileReaderImpl();

    public Path getTempDir() {
        return tempDir;
    }

    public void setTempDir(Path tempDir) {
        this.tempDir = tempDir;
    }

    @Test
    void read_ValidFile_ReturnsCorrectLines() throws IOException {
        Path tempFile = tempDir.resolve("test.csv");
        Files.write(tempFile, List.of("header", "apple,10", "banana,5"));

        List<String> result = fileReader.read(tempFile.toString());

        assertEquals(2, result.size());
        assertEquals("apple,10", result.get(0));
        assertEquals("banana,5", result.get(1));
    }

    @Test
    void read_EmptyFile_ReturnsEmptyList() throws IOException {
        Path emptyFile = tempDir.resolve("empty.csv");
        Files.write(emptyFile, List.of("header"));

        List<String> result = fileReader.read(emptyFile.toString());

        assertTrue(result.isEmpty());
    }

    @Test
    void read_FileDoesNotExist_ThrowsException() {
        String fakePath = tempDir.resolve("nonexistent.csv").toString();

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReader.read(fakePath));
        assertTrue(exception.getMessage().contains("Can't find file by path"));
    }
}
