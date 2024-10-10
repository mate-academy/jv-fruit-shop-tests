package core.basesyntax.service.write;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private static final Path VALID_FILE_PATH = Path.of(
            "src/test/java/resources/testFile.txt");
    private static final Path INVALID_FILE_PATH = Path.of(
            "src/test/resources/nonexistentDir/testFile.txt");
    private static final String TEST_DATA = "Hello, World!";

    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validData_fileCreatedSuccessfully() throws IOException {
        if (Files.exists(VALID_FILE_PATH)) {
            Files.delete(VALID_FILE_PATH);
        }
        new FileWriterImpl().write(VALID_FILE_PATH, TEST_DATA);
        assertDoesNotThrow(() -> fileWriter.write(VALID_FILE_PATH, TEST_DATA),
                "Writing data to the file should not throw an exception.");
        assertTrue(Files.exists(VALID_FILE_PATH), "The file should be created successfully.");
        String fileContent = Files.readString(VALID_FILE_PATH);
        assertEquals(TEST_DATA, fileContent);
    }

    @Test
    void write_emptyData_throwsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FileWriterImpl().write(VALID_FILE_PATH, "");
        });
        assertEquals("File path and data cannot be null or empty", exception.getMessage());
    }
}

