package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private ReaderServiceImpl readerServiceImpl;
    private final String resourcesPath = "src/test/resources";
    private final String validInputFileName = "input_correct_data.csv";
    private final String invalidInputFileName = "input_incorrect_data.csv";
    private final String emptyFileName = "input_empty_file.csv";

    @BeforeEach
    void setUp() {
        readerServiceImpl = new ReaderServiceImpl();
    }

    @Test
    void readFromFileOK() {
        String filePath = resourcesPath + File.separator + validInputFileName;
        List<String> lines = readerServiceImpl.readFromFileInput(filePath);
        assertEquals(9, lines.size());
    }

    @Test
    void readFromInvalidFileNotOk() {
        String filePath = resourcesPath + File.separator + invalidInputFileName;
        assertThrows(RuntimeException.class,
                () -> readerServiceImpl.readFromFileInput(filePath));
    }

    @Test
    void readFromNonExistentFileNotOk() {
        String nonExistentFilePath = "non-existent-file.csv";
        assertThrows(RuntimeException.class,
                () -> readerServiceImpl.readFromFileInput(nonExistentFilePath));
    }

    @Test
    void readFromFileInputShouldReturnEmptyListWhenFileIsEmpty() {
        String filePath = resourcesPath + File.separator + emptyFileName;
        List<String> lines = readerServiceImpl.readFromFileInput(filePath);
        assertTrue(lines.isEmpty(), "File should be empty");
    }
}
