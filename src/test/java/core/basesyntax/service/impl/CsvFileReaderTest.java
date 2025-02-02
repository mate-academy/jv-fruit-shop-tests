package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String VALID_FILE_PATH = "src/test/resources/valid-input.csv";
    private static final String NON_EXISTENT_FILE_PATH = "nonexistent.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty-input.csv";
    private static final String VALID_EMPTY_LINES_FILE_PATH =
            "src/test/resources/valid-input-empty-lines.csv";
    private static final String HEADER_CSV = "type,fruit,quantity";
    private static final String B_BANANA_20 = "b,banana,20";
    private static final String B_APPLE_100 = "b,apple,100";

    @Test
    void read_validFile_ok() {
        FileReader fileReader = new CsvFileReader();
        List<String> actual = fileReader.read(VALID_FILE_PATH);
        List<String> expected = List.of(HEADER_CSV, B_BANANA_20, B_APPLE_100);
        assertEquals(expected, actual);
    }

    @Test
    void read_fileDoesNotExist_notOk() {
        FileReader fileReader = new CsvFileReader();
        String nonExistentFilePath = NON_EXISTENT_FILE_PATH;
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(nonExistentFilePath));
        assertEquals("Cannot read file: " + nonExistentFilePath, exception.getMessage());
    }

    @Test
    void read_emptyFile_ok() {
        FileReader fileReader = new CsvFileReader();
        List<String> actual = fileReader.read(EMPTY_FILE_PATH);
        List<String> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void read_fileWithBlankLines_ok() {
        FileReader fileReader = new CsvFileReader();
        List<String> actual = fileReader.read(VALID_EMPTY_LINES_FILE_PATH);
        List<String> expected = new ArrayList<>(List.of(HEADER_CSV, B_BANANA_20, B_APPLE_100));
        assertEquals(expected, actual);
    }
}
