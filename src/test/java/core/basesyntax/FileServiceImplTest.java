package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.nio.FileService;
import core.basesyntax.service.nio.FileServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileServiceImplTest {
    private final FileService fileService = new FileServiceImpl();
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("testFile", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void writeAndRead_success() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".txt");
        String path = tempFile.toString();
        String content = "apple,100";

        fileService.write(path, content);
        List<String> lines = fileService.read(path);

        assertTrue(lines.get(0).contains("apple,100"));
    }

    @Test
    void read_emptyFile_returnsEmptyList() throws IOException {
        String path = tempFile.toString();

        // Clear any content
        Files.write(tempFile, new byte[0]);
        List<String> lines = fileService.read(path);

        assertTrue(lines.isEmpty());
    }

    @Test
    void write_invalidPath_throwsRuntimeException() {
        String invalidPath = "/invalid_path/test.txt";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileService.write(invalidPath, "orange,20"));

        assertTrue(exception.getMessage().contains("your writing path is not correct"));
    }

    @Test
    void read_invalidPath_throwsRuntimeException() {
        String invalidPath = "/invalid_path/nonexistent.txt";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileService.read(invalidPath));

        assertTrue(exception.getMessage().contains("your read path is not correct"));
    }
}
