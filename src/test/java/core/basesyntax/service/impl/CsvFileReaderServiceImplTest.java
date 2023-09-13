package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/validInput.csv";
    private static final String WRONG_PATH = "src/test/resources/wrong.csv";
    private static FileReaderService fileReaderService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    void readFromFile_validPath_ok() {
        assertFalse(fileReaderService.readFromFile(VALID_FILE_PATH).isEmpty());
    }

    @Test
    void readFromFile_wrongPath_notOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readFromFile(WRONG_PATH));
    }

    @Test
    void readFromFile_nullPath_notOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readFromFile(null));
    }
}
