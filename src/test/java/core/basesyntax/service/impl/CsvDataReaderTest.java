package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.Test;

public class CsvDataReaderTest {
    private static final String INPUT_VALID_FILE =
                                "src/test/test-resources/input-files/TestValidInputFile.txt";
    private static final String INPUT_EMPTY_FILE =
                                "src/test/test-resources/input-files/TestEmptyFile.txt";
    private static final String INPUT_NONEXISTENT_FILE =
                                "src/test/test-resources/input-files/doesNotExist.txt";
    private static final String INPUT_RANDOM_LINES_FILE =
                                "src/test/test-resources/input-files/TestRandomLines.txt";
    private static final CsvDataReader csvDataReader = new CsvDataReader();

    @Test
    public void readFileLines_allValidConditions_Ok() {
        List<String> actual = csvDataReader.readFileLines(INPUT_VALID_FILE);
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20");
        assertEquals(actual, expected);
    }

    @Test
    public void readFileLines_EmptyFileOnInput_NotOk() {
        assertThrows(RuntimeException.class, () -> csvDataReader.readFileLines(INPUT_EMPTY_FILE));
    }

    @Test
    public void readFileLines_nonexistentFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                    () -> csvDataReader.readFileLines(INPUT_NONEXISTENT_FILE));
    }

    @Test
    public void readFileLines_inputWithCustomLines_Ok() {
        List<String> actual = csvDataReader.readFileLines(INPUT_RANDOM_LINES_FILE);
        List<String> expected = List.of("ugpwrevonrov",
                                        "fwpueivwrnv",
                                        "vpaerhgp48g  oiwjeiof",
                                        "whvwvoiwn");
        assertEquals(actual, expected);
    }
}
