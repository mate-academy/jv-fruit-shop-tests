package core.basesyntax.service.impl;

import core.basesyntax.service.DataReaderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderServiceTest {
    private static final String INVALID_PATH = "src/test/resources/dummy-path/dummy.csv";
    private static final String VALID_PATH = "src/test/resources/input-test.csv";
    private static  final String PATH_TO_EMPTY_FILE  = "src/test/resources/input-test-empty-file.csv";
    private static  DataReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new CsvReaderService();
    }

    @Test
    void readData_indicateValidPathWithNonEmptyFile_Ok() {
        readerService.readData(VALID_PATH);
    }

    @Test
    void readData_indicateValidPatButWithEmptyFile_NotOk() {
        assertThrows(RuntimeException.class, () -> readerService.readData(PATH_TO_EMPTY_FILE));
    }

    @Test
    void  readData_indicateInvalidPath_NotOk() {
        assertThrows(RuntimeException.class, () -> readerService.readData(INVALID_PATH));
    }


}