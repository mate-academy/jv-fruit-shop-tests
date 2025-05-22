package service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String VALID_FILE_NAME = "testFile.txt";
    private static final String EMPTY_FILE_NAME = "emptyFile.txt";
    private static final String INVALID_PATH = "/invalid/directory/testFile.txt";
    private static final String OVERWRITE_FILE_NAME = "overwriteFile.txt";

    private void verifyFileContent(String fileName, String expectedContent) throws IOException {
        assertTrue(Files.exists(Paths.get(fileName)), "File should exist");
        String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
        assertTrue(fileContent.contains(expectedContent), "File should contain expected content");
    }

    @Test
    public void writeValidFile_ok() throws IOException {
        String content = "Line 1" + System.lineSeparator() + "Line 2";

        FileWriterImpl fileWriter = new FileWriterImpl();
        fileWriter.write(VALID_FILE_NAME, content);

        assertTrue(Files.exists(Paths.get(VALID_FILE_NAME)), "File should exist");

        verifyFileContent(VALID_FILE_NAME, content);

        Files.delete(Paths.get(VALID_FILE_NAME));
    }

    @Test
    public void writeEmptyContent_ok() throws IOException {
        String content = "";

        FileWriterImpl fileWriter = new FileWriterImpl();
        fileWriter.write(EMPTY_FILE_NAME, content);

        assertTrue(Files.exists(Paths.get(EMPTY_FILE_NAME)), "File should exist");
        verifyFileContent(EMPTY_FILE_NAME, content);

        Files.delete(Paths.get(EMPTY_FILE_NAME));
    }

    @Test
    public void writeInvalidPath_notOk() {
        String content = "Some content";

        FileWriterImpl fileWriter = new FileWriterImpl();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileWriter.write(INVALID_PATH, content);
        });

        assertTrue(exception.getMessage().contains("Error writing to file"),
                "Exception should be thrown for invalid path");
    }

    @Test
    public void writeOverwriteFile_ok() throws IOException {
        String initialContent = "Initial content";
        String newContent = "New content";

        FileWriterImpl fileWriter = new FileWriterImpl();

        fileWriter.write(OVERWRITE_FILE_NAME, initialContent);
        verifyFileContent(OVERWRITE_FILE_NAME, initialContent);

        fileWriter.write(OVERWRITE_FILE_NAME, newContent);
        verifyFileContent(OVERWRITE_FILE_NAME, newContent);

        Files.delete(Paths.get(OVERWRITE_FILE_NAME));
    }
}

