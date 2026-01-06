package core.basesyntax.serviceimpl;

import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FileReaderImplTest {
    private FileReader fileReader;

    @BeforeEach
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @TempDir
    Path tempDir;

    @Test
    public void readMethodWhenPathIsNullThrowsException() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileReader.read(null)
        );
        assertEquals("Path can`t be null!", exception.getMessage());
    }

    @Test
    public void readThrowsExceptionWhenFileDoesNotExist(){
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
