package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderImplTest {
    @TempDir
    private Path tempDir;

    private FileReader fileReader;

    @BeforeEach
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readMethodWhenPathIsNullThrowsException() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileReader.read(null)
        );
        assertEquals("Path can`t be null!", exception.getMessage());
    }

    @Test
    public void readThrowsExceptionWhenFileDoesNotExist() {
        Path missingFile = tempDir.resolve("missing.txt");
        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> fileReader.read(missingFile.toString())
        );
        assertEquals("Cannot read file: " + missingFile.toString(),
                exception.getMessage()
        );
    }

    @Test
    public void readFileFromPathSuccessfully() throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.write(file, List.of("test1", "test2"));
        List<String> result = fileReader.read(file.toString());
        assertEquals(List.of("test1", "test2"), result);
    }
}
