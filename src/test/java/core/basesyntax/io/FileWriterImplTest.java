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
    void setUp() throws IOException {
        fileWriter = new FileWriterImpl();
        tempFile = Files.createTempFile("test", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(tempFile)) {
            Files.delete(tempFile);
        }
    }

    @Test
    void write_validContentAndFilePath_success() throws IOException {
        String content = "Hello, World!";
        String filePath = tempFile.toString();

        fileWriter.write(content, filePath);

        assertTrue(Files.exists(tempFile), "File should exist after writing");
        String fileContent = Files.readString(tempFile);
        assertEquals(content, fileContent, "File content should match the input content");
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
