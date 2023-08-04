package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceTest {
    private static final String DEFAULT_PATH = "src/test/resources/";
    private static final String EXISTING_FILE_NAME = "test.csv";
    private static final String NOT_EXISTING_FILE_NAME = "failedTest.csv";
    private static final String NOT_EXISTING_FILE_ERROR_MESSAGE = "Can't open and read from the ";
    private static FileReaderService fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new CsvFileReaderService();
    }

    @Test
    void readFromFile_validFileName_ok() {
        File file = new File(DEFAULT_PATH + EXISTING_FILE_NAME);
        String actual = fileReader.readFromFile(file);
        String expected = "hello,world" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_notExistingFileName_notOk() {
        File file = new File(DEFAULT_PATH + NOT_EXISTING_FILE_NAME);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> fileReader.readFromFile(file));
        assertEquals(NOT_EXISTING_FILE_ERROR_MESSAGE
                            + NOT_EXISTING_FILE_NAME, thrown.getMessage());
    }
}
