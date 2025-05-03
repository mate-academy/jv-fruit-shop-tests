package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvDataReaderTest {
    private static final String INPUT_VALID_FILE =
                                "src/test/test-resources/input-files/TestValidInputFile.csv";
    private static final String INPUT_EMPTY_FILE =
                                "src/test/test-resources/input-files/TestEmptyFile.csv";
    private static final String INPUT_NONEXISTENT_FILE =
                                "src/test/test-resources/input-files/doesNotExist.txt";
    private static final String INPUT_RANDOM_LINES_FILE =
                                "src/test/test-resources/input-files/TestRandomLines.csv";
    private static CsvDataReader csvDataReader;

    @BeforeAll
    static void setUp() {
        csvDataReader = new CsvDataReader();
    }

    @Test
    void readFileLines_allValidConditions_Ok() {
        List<String> actual = csvDataReader.readFileLines(INPUT_VALID_FILE);
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20");
        assertIterableEquals(expected, actual);
    }

    @Test
    void readFileLines_EmptyFileOnInput_NotOk() {
        assertThrows(RuntimeException.class, () -> csvDataReader.readFileLines(INPUT_EMPTY_FILE));
    }

    @Test
    void readFileLines_nonexistentFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                    () -> csvDataReader.readFileLines(INPUT_NONEXISTENT_FILE));
    }

    @Test
    void readFileLines_inputWithCustomLines_Ok() {
        List<String> actual = csvDataReader.readFileLines(INPUT_RANDOM_LINES_FILE);
        List<String> expected = List.of("ugpwrevonrov",
                                        "fwpueivwrnv",
                                        "vpaerhgp48g  oiwjeiof",
                                        "whvwvoiwn");
        assertEquals(expected, actual);
    }
}
