package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    private static final String TEST_FILE_NAME = "outputFile.txt";
    private static final String NON_EXISTENT_FILE_NAME = "/invalid_path/outputFile.txt";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_ShouldWriteDataToFile_WhenFilePathIsValid(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve(TEST_FILE_NAME);
        String data = "Hello, World!";
        fileWriter.write(data, file.toString());
        String actualContent = Files.readString(file);
        assertEquals(data, actualContent, "Written content should match the provided data.");
    }

    @Test
    void write_ShouldThrowRuntimeException_WhenFilePathIsInvalid() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write("Test data", NON_EXISTENT_FILE_NAME),
                "Writing to an invalid path should throw RuntimeException.");
        assertEquals("Failed to write to file: " + NON_EXISTENT_FILE_NAME,
                exception.getMessage(),
                "Exception message should match the expected error message.");
    }
}
