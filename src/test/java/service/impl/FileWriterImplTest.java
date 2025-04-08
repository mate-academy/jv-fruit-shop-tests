package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    @Test
    void writeValidFile_ok() throws IOException {
        String testFile = "testFile.txt";
        String content = "TestLine 1" + System.lineSeparator() + "TestLine 2";
        Path filePath = Paths.get(testFile);

        FileWriterImpl writer = new FileWriterImpl();
        writer.write(testFile, content);
        assertTrue(Files.exists(filePath), "Expected to get existing file");
        String fileContent = new String(Files.readAllBytes(filePath));
        assertTrue(fileContent.contains("TestLine 1"), "File must contain TestLine 1");
        assertTrue(fileContent.contains("TestLine 2"), "File must contain TestLine 2");

        Files.delete(filePath);
    }

    @Test
    void writeEmptyFile_ok() throws IOException {
        String testFile = "emptyTestFile.txt";
        String content = "";
        Path filePath = Paths.get(testFile);

        FileWriterImpl writer = new FileWriterImpl();
        writer.write(testFile, content);
        assertTrue(Files.exists(filePath), "Expected to get existing file");
        String fileContent = new String(Files.readAllBytes(filePath));
        assertEquals("", fileContent, "Expected to get an empty file");

        Files.delete(filePath);
    }

    @Test
    void writeInvalidFile_notOk() {
        String testFile = "no/such/folder/invalidTestFile.txt";
        String content = "This is content";

        FileWriterImpl writer = new FileWriterImpl();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            writer.write(testFile, content);
        });

        assertTrue(exception.getMessage().contains("Failed to write to file: "),
                "Correct exception message must be thrown");
    }
}
