package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/input_file.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty_file.csv";
    private static final String NON_EXISTENT_FILE_PATH = "src/test/resources/non_existent_file.csv";
    private FileReaderService csvFileReader;

    @BeforeEach
    void setUp() {
        csvFileReader = new CsvFileReader();
    }

    @Test
    void read_validFile_ok() {
        String filePath = INPUT_FILE_PATH;
        assertDoesNotThrow(() -> csvFileReader.read(filePath));
    }

    @Test
    void read_emptyFile_notOk() {
        String filePath = EMPTY_FILE_PATH;
        assertThrows(Exception.class, () -> csvFileReader.read(filePath));
    }

    @Test
    void read_nonExistentFile_notOk() {
        String filePath = NON_EXISTENT_FILE_PATH;
        assertThrows(Exception.class, () -> csvFileReader.read(filePath));
    }
}
