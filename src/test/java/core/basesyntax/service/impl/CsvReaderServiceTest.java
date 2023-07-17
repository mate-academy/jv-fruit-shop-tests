package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataReaderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvReaderServiceTest {
    private static final String INVALID_PATH = "src/test/resources/non/existed/dummy.csv";
    private static final String VALID_PATH = "src/test/resources/input-test.csv";
    private static final String PATH_TO_EMPTY_FILE =
            "src/test/resources/empty-file.csv";
    private static DataReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new CsvReaderService();
    }

    @Test
    void readData_indicateValidPathWithNonEmptyFile_ok() {
        readerService.readData(VALID_PATH);
    }

    @Test
    void readData_indicateValidPatButWithEmptyFile_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readData(PATH_TO_EMPTY_FILE));
    }

    @Test
    void readData_indicateInvalidPath_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readData(INVALID_PATH));
    }
}
