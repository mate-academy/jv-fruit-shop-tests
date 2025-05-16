package service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileWriter;

class FileWriterImplTest {

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

        fileWriter.write(filePath.toString(), "fruit,quantity\napple,20\n");

        String result = Files.readString(filePath);
        Assertions.assertEquals("fruit,quantity\napple,20\n", result);
    }

    @Test
    void invalidPath_notOk() {
        String invalidFilePath = File.separator
                + "invalidFilePath" + File.separator
                + "directory" + File.separator + "finalReport.csv";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write(invalidFilePath, "fruit,quantity"));

        assertTrue(exception.getMessage().contains("Error to write file!"));
    }

    @Test
    void emptyContent_notOk() throws IOException {
        filePath = Files.createTempFile("reportToRead", ".csv");
        fileWriter.write(filePath.toString(), "");

        String fileContent = Files.readString(filePath);
        Assertions.assertEquals("", fileContent);

        FileWriterImpl fileWriter = new FileWriterImpl();
        fileWriter.write(filePath.toString(), "fruit,quantity\napple,20\n");

        String result = Files.readString(filePath);
        assertEquals("fruit,quantity\napple,20\n", result);
    }
}
