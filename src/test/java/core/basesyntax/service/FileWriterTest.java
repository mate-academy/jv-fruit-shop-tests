package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String CSV_FILE_PATH = "src/test/resources/test.csv";
    private static final String INVALID_FORMAT_FILENAME = "src/test/resources/image.jpg";
    private static final String NON_EXISTENT_DIRPATH = "nonexistent-directory/test.txt";
    private static final String REPORT = "some text...";
    private static final String TEXT_FILE_PATH = "src/test/resources/test.txt";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriter();
    }

    @Test
    void write_ValidFormatFile_Ok() {
        assertDoesNotThrow(() -> fileWriter.write(TEXT_FILE_PATH, REPORT));
        assertTrue(Files.exists(Paths.get(TEXT_FILE_PATH)));
        assertDoesNotThrow(() -> fileWriter.write(CSV_FILE_PATH, REPORT));
        assertTrue(Files.exists(Paths.get(CSV_FILE_PATH)));
    }

    @Test
    public void write_FileInvalidFormat_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileWriter.write(INVALID_FORMAT_FILENAME, REPORT);
        });
    }

    @Test
    void write_NonExistentDirectory_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(NON_EXISTENT_DIRPATH, REPORT);
        });
    }
}
