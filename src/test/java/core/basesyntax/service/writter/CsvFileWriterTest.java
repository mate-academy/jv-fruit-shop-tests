package core.basesyntax.service.writter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String VALID_PATH = "src/test/resources/output.csv";
    private static final String INVALID_PATH = "s61/test/resources/output.csv";
    private static final String EMPTY_STRING = "";
    private static FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new CsvFileWriter();
    }

    @Test
    void writeFile_withValidPath_Ok() {
        boolean actual = fileWriter.write(EMPTY_STRING, VALID_PATH);
        Assertions.assertTrue(actual);
    }

    @Test
    void writeFile_withInvalidPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write(EMPTY_STRING, INVALID_PATH));
    }

    @Test
    void writeFile_withNullPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write(EMPTY_STRING, null));
    }

    @Test
    void writeFile_withEmptyPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write(EMPTY_STRING, EMPTY_STRING));
    }

    @Test
    void writeFile_withNullContent_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write(null, VALID_PATH));
    }
}
