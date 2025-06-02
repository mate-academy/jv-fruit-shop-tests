package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.nio.FileReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private static Path tempFile;
    private FileReaderServiceImpl fileReaderService;

    @BeforeAll
    static void setupAll() throws IOException {
        // Створюємо тимчасовий файл з кількома рядками
        tempFile = Files.createTempFile("testFile", ".txt");
        Files.write(tempFile, List.of("line1", "line2", "line3"));
    }

    @AfterAll
    static void cleanupAll() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void read_shouldReturnLines_whenFileExists() {
        List<String> lines = fileReaderService.read(tempFile.toString());
        assertNotNull(lines);
        assertEquals(3, lines.size());
        assertEquals("line1", lines.get(0));
        assertEquals("line2", lines.get(1));
        assertEquals("line3", lines.get(2));
    }

    @Test
    void read_shouldThrowRuntimeException_whenFileDoesNotExist() {
        String fakePath = "non_existent_file.txt";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReaderService.read(fakePath));
        assertTrue(exception.getMessage().contains("File does not exist at path"));
    }

    @Test
    void read_shouldThrowRuntimeException() throws IOException {
        // Імітуємо IOException створенням шляху до папки замість файлу
        Path directoryPath = Files.createTempDirectory("tempDir");
        try {
            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> fileReaderService.read(directoryPath.toString()));
            assertTrue(exception.getMessage().contains("Failed to read file at"));
            assertNotNull(exception.getCause());
            assertTrue(exception.getCause() instanceof IOException);
        } finally {
            Files.deleteIfExists(directoryPath);
        }
    }
}
