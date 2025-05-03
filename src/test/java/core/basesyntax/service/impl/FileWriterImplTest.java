package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final Path TEST_FILE_PATH = Path.of("test.txt");
    private FileWriterService fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void testWriteToFile_OK() throws IOException {
        String testData = "Test data to be written to the file.";
        fileWriter.writeToFile(testData, TEST_FILE_PATH);
        try (BufferedReader reader = Files.newBufferedReader(TEST_FILE_PATH)) {
            String line = reader.readLine();
            assertEquals(testData, line);
        }
    }

    @Test
    void writeToFile_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> fileWriter.writeToFile(null, TEST_FILE_PATH));
    }

    @Test
    void writeToFile_invalidPath_throwsIO_Exception() {
        Path invalidPath = Path.of("nonExistent/Directory/test.txt");
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile("Test record", invalidPath));
    }

    @Test
    void testWriteToFile_NullPath_ThrowsException() {
        assertThrows(NullPointerException.class,
                () -> fileWriter.writeToFile("Test data", null));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TEST_FILE_PATH);
    }
}
