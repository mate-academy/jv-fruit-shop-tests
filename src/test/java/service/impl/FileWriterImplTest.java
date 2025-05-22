package service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileWriter;

class FileWriterImplTest {
    private static final String EXAMPLE_CONTENT = "fruit,quantity\napple,20\n";
    private static final String EMPTY_CONTENT = "";
    private static final String INVALID_FILE_PATH = File.separator + "invalidFilePath"
            + File.separator + "directory" + File.separator + "finalReport.csv";

    private Path filePath;

    private FileWriter fileWriter;

    @BeforeEach
    void setUp() throws IOException {
        filePath = Files.createTempFile("reportToRead", ".csv");
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(filePath);
    }

    @Test
    void writeToFile_ok() throws IOException {
        fileWriter.write(filePath.toString(), EXAMPLE_CONTENT);

        String result = Files.readString(filePath);
        assertEquals(EXAMPLE_CONTENT, result);
    }

    @Test
    void write_invalidPath_shouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write(INVALID_FILE_PATH, EXAMPLE_CONTENT));

        assertTrue(exception.getMessage().contains("Error to write file!"));
    }

    @Test
    void write_shouldToShowEmptyContent() throws IOException {
        fileWriter.write(filePath.toString(), EMPTY_CONTENT);

        String fileContent = Files.readString(filePath);
        assertEquals(EMPTY_CONTENT, fileContent);
    }

    @Test
    void write_shouldToOverWriteContent() throws IOException {
        fileWriter.write(filePath.toString(), EXAMPLE_CONTENT);

        String fileContent = Files.readString(filePath);
        assertEquals(EXAMPLE_CONTENT, fileContent);
    }
}
