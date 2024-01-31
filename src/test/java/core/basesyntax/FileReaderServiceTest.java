package core.basesyntax;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileReaderService;

public class FileReaderServiceTest {
    private static final String FILE_NAME = "test_file";
    private static final String FILE_TYPE = ".csv";
    private static final String FILE_CONTENT = "apple,10" + System.lineSeparator() + "banana,5";
    private static final String NON_EXISTING_FILE = "non_existing_file";
    private FileReaderService fileReaderService;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReaderService = new FileReaderService();
        tempFile = Files.createTempFile(FILE_NAME, FILE_TYPE);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testReadFromFile_WithValidFile() throws IOException {
        Files.writeString(tempFile, FILE_CONTENT);
        List<String> lines = fileReaderService.readFromFile(tempFile.toString());
        assertEquals(2, lines.size());
        assertEquals("apple,10", lines.get(0));
        assertEquals("banana,5", lines.get(1));
    }

    @Test
    void testReadFromFile_WithEmptyFile() throws IOException {
        Files.writeString(tempFile, "");
        List<String> lines = fileReaderService.readFromFile(tempFile.toString());
        Assertions.assertTrue(lines.isEmpty());
    }

    @Test
    void testReadFromFile_WithNonExistingFile() {
        assertThrows(RuntimeException.class, () -> {
            fileReaderService.readFromFile(NON_EXISTING_FILE);
        });
    }
}
