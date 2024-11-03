package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileReaderServiceImplTest {
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();

    @Test
    public void testRead_ok(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile.txt");
        List<String> expectedContent = List.of("line1", "line2", "line3");
        Files.write(testFile, expectedContent);

        List<String> actualContent = fileReaderService.read(testFile.toString());

        assertEquals(expectedContent, actualContent,
                "The content read from the file should match the expected content");
    }

    @Test
    public void testRead_fileNotFound_notOk() {
        String invalidPath = "nonexistent/path/to/file.txt";

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileReaderService.read(invalidPath),
                "Expected RuntimeException for nonexistent file"
        );

        assertTrue(exception.getMessage().contains("Can't read from file: " + invalidPath),
                "Exception message should indicate the file that couldn't be read");

        assertNotNull(exception.getCause(), "The exception should have a cause");
        assertTrue(exception.getCause() instanceof IOException,
                "The cause should be an IOException");
    }
}
