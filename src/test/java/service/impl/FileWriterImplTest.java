package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

public class FileWriterImplTest {
    @Test
    public void writeValidFile_ok() throws IOException {
        String fileName = "testFile.txt";
        String content = "Line 1\nLine 2";

        FileWriterImpl fileWriter = new FileWriterImpl();
        fileWriter.write(fileName, content);

        assertTrue(Files.exists(Paths.get(fileName)), "File should exist");
        String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
        assertTrue(fileContent.contains("Line 1"), "File should contain 'Line 1'");
        assertTrue(fileContent.contains("Line 2"), "File should contain 'Line 2'");

        Files.delete(Paths.get(fileName));
    }

    @Test
    public void writeEmptyContent_ok() throws IOException {
        String fileName = "emptyFile.txt";
        String content = "";

        FileWriterImpl fileWriter = new FileWriterImpl();
        fileWriter.write(fileName, content);

        assertTrue(Files.exists(Paths.get(fileName)), "File should exist");
        String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
        assertEquals("", fileContent, "File should be empty");

        Files.delete(Paths.get(fileName));
    }

    @Test
    public void writeInvalidPath_notOk() {
        String invalidPath = "/invalid/directory/testFile.txt";
        String content = "Some content";

        FileWriterImpl fileWriter = new FileWriterImpl();

        Exception exception = assertThrows(IOException.class, () -> {
            fileWriter.write(invalidPath, content);
        });

        assertTrue(exception.getMessage().contains("Error writing to file"),
                "Exception should be thrown for invalid path");
    }
}

