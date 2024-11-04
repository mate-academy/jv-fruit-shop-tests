package core.basesyntax.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_ShouldWriteDataToFile_WhenFilePathIsValid(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("outputFile.txt");
        String data = "Hello, World!";
        fileWriter.write(data, file.toString());
        String actualContent = Files.readString(file);
        assertEquals(data, actualContent, "Written content should match the provided data.");
    }

    @Test
    void write_ShouldThrowRuntimeException_WhenFilePathIsInvalid() {
        String invalidFilePath = "/invalid_path/outputFile.txt";
        assertThrows(RuntimeException.class, () -> fileWriter.write("Test data", invalidFilePath),
                "Writing to an invalid path should throw RuntimeException.");
    }
}
