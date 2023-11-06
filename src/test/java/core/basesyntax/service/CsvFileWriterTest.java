package core.basesyntax.service;

import core.basesyntax.impl.CsvFileWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileWriterTest {
    private static final String VALID_PATH = "src/test/resources/validFileOutput.csv";
    private static final String INVALID_PATH = "src/test/resources/notAFolder/validFileOutput.csv";
    private static final String EMPTY_STRING = "";
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new CsvFileWriter();
    }

    @Test
    void writeToFile_validPath_ok() {
        boolean actual = fileWriterService.writeToFile(EMPTY_STRING, VALID_PATH);
        Assertions.assertTrue(actual);
    }

    @Test
    void writeToFile_nullData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(null, VALID_PATH));
    }

    @Test
    void writeToFile_invalidPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(EMPTY_STRING, INVALID_PATH));
    }

    @Test
    void writeToFile_nullPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(EMPTY_STRING, null));
    }
}
