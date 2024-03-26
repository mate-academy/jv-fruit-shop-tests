package core.basesyntax.service.impl;

import core.basesyntax.exceptions.InvalidFileException;
import core.basesyntax.service.FileWriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

class FileWriterServiceImplTest {
    private static final String VALID_CONTENT = "content";
    private static final String EMPTY_FILE_PATH = "";
    private static final String TEST_FILE_PATH = "testFile.txt";
    private FileWriterService fileWriterService;

    @BeforeEach
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @BeforeEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    public void writeToFile_SuccessfulWrite() throws IOException {
        fileWriterService.writeToFile(TEST_FILE_PATH, VALID_CONTENT);
        StringBuilder actualContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                actualContent.append(line);
            }
        }
        assertEquals(VALID_CONTENT, actualContent.toString());
    }

    @Test
    public void writeToFile_FileNotFoundException() {
        String expected = "Can't write content to the file: " + EMPTY_FILE_PATH;

        InvalidFileException exception = assertThrows(InvalidFileException.class,
                () -> fileWriterService.writeToFile(EMPTY_FILE_PATH, VALID_CONTENT));

        assertEquals(expected, exception.getMessage());
    }
}
