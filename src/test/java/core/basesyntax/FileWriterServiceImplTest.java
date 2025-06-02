package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.nio.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private static Path tempFile;
    private FileWriterServiceImpl fileWriterService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @BeforeAll
    static void createTempFile() throws IOException {
        tempFile = Files.createTempFile("testWriteFile", ".txt");
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void write_shouldWriteLinesSuccessfully() throws IOException {
        List<String> lines = List.of("line1", "line2", "line3");
        fileWriterService.write(tempFile.toString(), lines);

        List<String> readLines = Files.readAllLines(tempFile);
        assertEquals(lines.size(), readLines.size());
        assertEquals(lines, readLines);
    }

    @Test
    void write_shouldThrowRuntime() {

        Path directoryPath = null;
        try {
            directoryPath = Files.createTempDirectory("tempDir");
            Path finalDirectoryPath = directoryPath;

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> fileWriterService.write(finalDirectoryPath.toString(), List.of("line")));

            assertTrue(exception.getMessage()
                    .contains("Write path is incorrect or not accessible"));
            assertNotNull(exception.getCause());
            assertTrue(exception.getCause() instanceof IOException);
        } catch (IOException e) {
            fail("Unexpected IOException in test setup");
        } finally {
            if (directoryPath != null) {
                try {
                    Files.deleteIfExists(directoryPath);

                } catch (IOException ignored) {
                    System.out.println("Invalid");
                }
            }
        }
    }

    @Test
    void write_shouldThrowNullPointerException_whenPathIsNull() {
        assertThrows(NullPointerException.class, () -> fileWriterService
                .write(null, List.of("line")));
    }

    @Test
    void write_shouldCreateFileIfNotExists() throws IOException {
        Path nonExistingFile = Paths.get(System
                .getProperty("java.io.tmpdir"), "nonExistingFile.txt");
        try {
            if (Files.exists(nonExistingFile)) {
                Files.delete(nonExistingFile);
            }

            fileWriterService.write(nonExistingFile.toString(), List.of("test"));
            assertTrue(Files.exists(nonExistingFile));

            List<String> lines = Files.readAllLines(nonExistingFile);
            assertEquals(1, lines.size());
            assertEquals("test", lines.get(0));
        } finally {
            Files.deleteIfExists(nonExistingFile);
        }
    }

    @Test
    void write_shouldWriteLinesToFile() throws IOException {
        Path tempFile = Files.createTempFile("test", ".txt");
        try {
            List<String> lines = List.of("one", "two");
            fileWriterService.write(tempFile.toString(), lines);
            List<String> readLines = Files.readAllLines(tempFile);
            assertEquals(lines, readLines);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void write_shouldThrowRuntimes() {
        String invalidPath = "/root/forbidden/file.txt"; // very likely inaccessible
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> fileWriterService.write(invalidPath, List.of("data")));
        assertTrue(ex.getMessage().contains("Write path is incorrect"));
    }
}
