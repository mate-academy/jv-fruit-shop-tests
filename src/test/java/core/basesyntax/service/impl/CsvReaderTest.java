package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvReaderTest {
    private static final String VALID_PATH_TO_INPUT_FILE = "src/test/resources/testFiles/input.csv";
    private static final String WRONG_FORMAT_FILE = "src/test/resources/testFiles/input.txt";
    private static final String INVALID_PATH_TO_INPUT_FILE =
            "src/test/resources/testFiles/awea" + ".csv";
    private static Reader csvReader;

    @BeforeAll
    static void beforeAll() {
        csvReader = new CsvReader();
    }

    @Test
    void readFile_validPath_Ok() {
        List<String> expected;
        List<String> actual;
        try {
            expected = Files.readAllLines(Path.of(VALID_PATH_TO_INPUT_FILE));
            actual = csvReader.readFile(VALID_PATH_TO_INPUT_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file " + VALID_PATH_TO_INPUT_FILE);
        }
        assertIterableEquals(expected, actual);
    }

    @Test
    void readFile_InvalidPath_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            csvReader.readFile(INVALID_PATH_TO_INPUT_FILE);
        });
        String expected = "Cannot read file " + INVALID_PATH_TO_INPUT_FILE;
        assertEquals(expected, runtimeException.getMessage());
    }

    @Test
    void readFile_readWrongFormat_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            csvReader.readFile(WRONG_FORMAT_FILE);
        });
        String format = WRONG_FORMAT_FILE.substring(WRONG_FORMAT_FILE.indexOf('.') + 1);
        String expected = "Invalid format file: " + format;
        assertEquals(expected, runtimeException.getMessage());
    }

    @Test
    void readFile_pathToFileIsNull_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            csvReader.readFile(null);
        });

        String expected = "Path to file must not be null.";
        assertEquals(expected, runtimeException.getMessage());
    }
}
