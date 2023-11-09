package core.basesyntax.service.writter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

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
        assertTrue(actual);
    }

    @Test
    void writeFile_withInvalidPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(EMPTY_STRING, INVALID_PATH));
    }

    @Test
    void writeFile_withNullPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(EMPTY_STRING, null));
    }

    @Test
    void writeFile_withEmptyPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(EMPTY_STRING, EMPTY_STRING));
    }

    @Test
    void writeFile_withNullContent_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(null, VALID_PATH));
    }

    @AfterEach
    void tearDown() {
        File file = new File(VALID_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}
