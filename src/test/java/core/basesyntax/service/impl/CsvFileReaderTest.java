package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {
    private FileReaderService csvFileReader;

    @BeforeEach
    void initializeCsvFileReader() {
        csvFileReader = new CsvFileReader();
    }

    @Test
    void read_validFile_ok() {
        String filePath = "src/test/resources/input_file.csv";
        assertDoesNotThrow(() -> csvFileReader.read(filePath));
    }

    @Test
    void read_emptyFile_notOk() {
        String filePath = "src/test/resources/empty_file.csv";
        assertThrows(Exception.class, () -> csvFileReader.read(filePath));
    }

    @Test
    void read_nonExistentFile_notOk() {
        String filePath = "src/test/resources/non_existent_file.csv";
        assertThrows(Exception.class, () -> csvFileReader.read(filePath));
    }
}
