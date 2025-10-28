package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class FileWriterImplTest {

    private FileWriter fileWriter;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileWriter = new FileWriterImpl();
        tempFile = Files.createTempFile("test-file", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void write_validInput_writesDataToFile() throws IOException {
        String data = "apple,10\nbanana,20";
        fileWriter.write(data, tempFile.toString());
        String content = Files.readString(tempFile);
        assertEquals(data, content);
    }

    @Test
    void write_nullData_throwsException() {
        Executable action = () -> fileWriter.write(null, tempFile.toString());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("Data or file path cannot be null", exception.getMessage());
    }

    @Test
    void write_nullFilePath_throwsException() {
        Executable action = () -> fileWriter.write("data", null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("Data or file path cannot be null", exception.getMessage());
    }

    @Test
    void write_invalidPath_throwsRuntimeException() {
        Executable action = () -> fileWriter.write("data", "/invalid/path/file.txt");
        RuntimeException exception = assertThrows(RuntimeException.class, action);
        assertTrue(exception.getMessage().contains("Data cannot be written to file"));
    }
}
