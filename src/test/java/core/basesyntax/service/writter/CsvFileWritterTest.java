package core.basesyntax.service.writter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWritterTest {
    private static final String VALID_PATH = "src/test/resources/output.csv";
    private static final String INVALID_PATH = "in/valid/path/output.csv";
    private static final String EMPTY_STRING = "";
    private static FileWritter fileWritter;

    @BeforeEach
    void setUp() {
        fileWritter = new CsvFileWritter();
    }

    @Test
    void writeFile_withValidPath_Ok() {
        boolean actual = fileWritter.write(EMPTY_STRING, VALID_PATH);
        Assertions.assertTrue(actual);
    }

    @Test
    void writeFile_withInvalidPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWritter.write(EMPTY_STRING, INVALID_PATH));
    }

    @Test
    void writeFile_withNullPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWritter.write(EMPTY_STRING, null));
    }

    @Test
    void writeFile_withEmptyPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWritter.write(EMPTY_STRING, EMPTY_STRING));
    }

    @Test
    void writeFile_withNullContent_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWritter.write(null, VALID_PATH));
    }
}
