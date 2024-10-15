package core.basesyntax.service.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private static final String VALID_FILE_PATH = "src/test/java/resources/testFile.csv";
    private static final String EMPTY_FILE_PATH = "src/test/java/resources/emptyFile.csv";
    private static final String NON_EXISTENT_FILE_PATH
            = "invalid/path/to/file.txt";
    private FileReading fileReader;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReadingImpl();
    }

    @Test
    void read_ValidFilePath_ReturnsContentList() throws NoSuchFileException {
        List<String> result = fileReader.read(VALID_FILE_PATH);
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Hello", result.get(0));
        assertEquals("World", result.get(1));
        assertEquals("Test", result.get(2));
    }

    @Test
    void read_EmptyFile_ReturnsEmptyList() {
        List<String> result = fileReader.read(EMPTY_FILE_PATH);
        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result should be an empty list for an empty file");
    }

    @Test
    void read_NonExistentFile_ThrowsRuntimeException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileReader.read(NON_EXISTENT_FILE_PATH);
        });
        assertTrue(exception.getMessage().contains("Error reading the file"));
    }

    @Test
    void read_NullFilePath_ThrowsIllegalArgumentException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fileReader.read(null);
        });
        assertEquals("File path cannot be null", exception.getMessage());
    }
}
