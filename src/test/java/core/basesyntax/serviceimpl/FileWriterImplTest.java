package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileWriterImplTest {
    @TempDir
    private Path tempDir;

    private FileWriter fileWriter;

    @BeforeEach
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void whenLineIsNullReturnsException() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(null, "test")
        );
        assertEquals("Lines can`t be null!", exception.getMessage());
    }

    @Test
    public void whenPathIsNullReturnsException() {
        List<String> list = new ArrayList<>();
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(list, null)
        );
        assertEquals("Path can`t be null!", exception.getMessage());
    }

    @Test
    public void writeLinesSuccessfully() throws IOException {
        Path file = tempDir.resolve("test.txt");
        List<String> lines = List.of("One", "Two", "Three");
        fileWriter.write(lines, file.toString());
        List<String> result = Files.readAllLines(file);
        assertEquals(lines, result);
    }

    @Test
    public void writeReturnsExceptionWhenPathIsInvalid() {
        Path directory = tempDir.resolve("directory.txt");
        assertDoesNotThrow(() -> Files.createDirectory(directory));
        List<String> lines = List.of("test", "test1", "test2");
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(lines, directory.toString())
        );
        assertEquals("Cannot write file: " + directory.toString(), exception.getMessage());
    }
}
