package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE_PATH = "test_output.txt";
    private static final String INVALID_PATH = "/invalid_path/test.txt";
    private FileWriterImpl fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validData_success() throws IOException {
        String testData = "Hello, world!";

        fileWriter.write(testData, TEST_FILE_PATH);

        String fileContent = Files.readString(Path.of(TEST_FILE_PATH));
        assertEquals(testData, fileContent, "Failed in write_validData_success: "
                + "File content should match the written data");

        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    void write_invalidPath_throwsRuntimeException() {
        String testData = "Some data";

        Exception exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write(testData, INVALID_PATH));

        assertTrue(exception.getMessage().contains("Can't write to file"));
    }
}
