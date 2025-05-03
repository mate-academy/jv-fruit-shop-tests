package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exceptions.InvalidFileException;
import core.basesyntax.service.FileWriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String VALID_CONTENT = "content";
    private static final String EMPTY_FILE_PATH = "";
    private static final String TEST_FILE_PATH = "testFile.txt";
    private static final String SEPARATOR = System.lineSeparator();
    private FileWriterService fileWriterService;

    @BeforeEach
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_SuccessfulWrite() throws IOException {
        String additionalContent = "additional content";
        String expectedContent = VALID_CONTENT + SEPARATOR + additionalContent;

        fileWriterService.writeToFile(TEST_FILE_PATH, expectedContent);
        StringBuilder actualContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                actualContent.append(line).append(SEPARATOR);
            }
        }
        assertEquals(expectedContent, actualContent.toString().trim());
    }

    @Test
    public void writeToFile_FileNotFoundException() {
        String expected = "Can't write content to the file: " + EMPTY_FILE_PATH;

        InvalidFileException exception = assertThrows(InvalidFileException.class,
                () -> fileWriterService.writeToFile(EMPTY_FILE_PATH, VALID_CONTENT));

        assertEquals(expected, exception.getMessage());
    }
}
