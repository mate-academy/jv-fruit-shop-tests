package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FileReadingFailureException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvReaderTest {
    private static final String ALL_VALID_INPUT_FILE = "src/test/resources/AllValidInput.csv";
    private static final String EMPTY_FILE = "src/test/resources/Empty.csv";
    private static final String NON_EXISTENT_FILE = "src/test/resources/NoFile.csv";
    private static final String NOT_CSV_FILE = "src/test/resources/NotCSV.txt";
    private static final String FILE_WITH_RANDOM_LINES = "src/test/resources/RandomLines.csv";
    private static final Class<FileReadingFailureException> EXPECTED_EXCEPTION_CLASS
        = FileReadingFailureException.class;


    private static CsvReader csvReader;

    @BeforeAll
    static void setup() {
        csvReader = new CsvReader();
    }

    @Test
    void read_AllValidConditions_Ok() {
        List<String> actual = csvReader.read(ALL_VALID_INPUT_FILE);
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "p,potato,150");
        assertIterableEquals(expected, actual);
    }

    @Test
    void read_EmptyFile_throwException() {
        assertThrows(EXPECTED_EXCEPTION_CLASS,
            () -> csvReader.read(EMPTY_FILE));
    }

    @Test
    void read_ReadNonExistentFile_throwException() {
        assertThrows(EXPECTED_EXCEPTION_CLASS,
            () -> csvReader.read(NON_EXISTENT_FILE));
    }

    @Test
    void read_providedFileExtensionNotCsv_throwException() {
        assertThrows(EXPECTED_EXCEPTION_CLASS,
            () -> csvReader.read(NOT_CSV_FILE));
    }

    @Test
    void read_inputFileWithCustomLines_Ok() {
        List<String> actual = csvReader.read(FILE_WITH_RANDOM_LINES);
        List<String> expected = List.of("ugpwrevonrov",
            "fwpueivwrnv",
            "vpaerhgp48g  oiwjeiof",
            "whvwvoiwn");
        assertEquals(expected, actual);
    }

}