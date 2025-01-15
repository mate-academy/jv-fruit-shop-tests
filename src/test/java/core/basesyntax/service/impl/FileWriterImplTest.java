package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {

    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_validData_writesToFile() throws IOException {
        String expectedData = "Test data for file writer";
        Path tempFile = Files.createTempFile("testFile", ".csv");

        fileWriter.write(expectedData, tempFile.toString());

        String actualData = Files.readString(tempFile);
        assertEquals(expectedData, actualData);

        Files.deleteIfExists(tempFile);
    }

    @Test
    void write_invalidPath_throwsRuntimeException() {
        String data = "Some data";
        String invalidPath = "invalid_path/testFile.csv";

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileWriter.write(data, invalidPath)
        );

        assertTrue(exception.getMessage().contains("Error writing to file"));
    }

    @Test
    void write_existingFile_overwritesContent() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".csv");
        Files.writeString(tempFile, "Old content", StandardOpenOption.WRITE);

        String newContent = "New content";

        fileWriter.write(newContent, tempFile.toString());

        String actualData = Files.readString(tempFile);
        assertEquals(newContent, actualData);

        Files.deleteIfExists(tempFile);
    }
}
