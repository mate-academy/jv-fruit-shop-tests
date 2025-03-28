package core.basesyntax.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriter fileWriter;
    private Path tempFile;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
        tempFile = Path.of("src/test/resources/test.csv");
        try {
            Files.createDirectories(tempFile.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Failed to set up test file", e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            if (Files.exists(tempFile)) {
                Files.delete(tempFile);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete test file", e);
        }
    }

    @Test
    void write_validContentAndFilePath_success() {
        String content = "Hello, World!";
        String filePath = tempFile.toString();

        fileWriter.write(content, filePath);

        assertTrue(Files.exists(tempFile), "File should exist after writing");
        try {
            String fileContent = Files.readString(tempFile);
            assertEquals(content, fileContent, "File content should match the input content");
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test file", e);
        }
    }

    @Test
    void write_nullFilePath_throwsIllegalArgumentException() {
        String content = "Hello, World!";
        String filePath = null;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fileWriter.write(content, filePath)
        );
        assertEquals("File path and content cannot be null", exception.getMessage());
    }

    @Test
    void write_nullContent_throwsIllegalArgumentException() {
        String content = null;
        String filePath = tempFile.toString();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fileWriter.write(content, filePath)
        );
        assertEquals("File path and content cannot be null", exception.getMessage());
    }

    @Test
    void write_invalidFilePath_throwsRuntimeException() {
        String content = "Hello, World!";
        String filePath = "/invalid/path/test.txt"; // Assuming this path is invalid

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(content, filePath)
        );
        assertTrue(exception
                .getMessage()
                .startsWith("An error occurred while writing to the file"));
    }
}
