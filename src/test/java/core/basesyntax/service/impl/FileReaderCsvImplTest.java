package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FileReaderCsvImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderCsvImplTest {
    private static final String RESOURCES_PATH = "src/test/resources/";
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderCsvImpl();
    }

    @Test
    void readFromFile_ValidFile_Ok() {
        String fileName = "validTest.csv";
        List<String> expected = List.of("b,apple,100", "s,banana,150");
        List<String> actual = fileReader.readFromFile(RESOURCES_PATH + fileName);
        assertEquals(expected, actual,
                "Should read all lines excluding the header from a valid file.");
    }

    @Test
    void readFromFile_NonExistentFile_NotOk() {
        String fileName = "nonExistent.csv";
        assertThrows(RuntimeException.class, () -> fileReader
                        .readFromFile(RESOURCES_PATH + fileName),
                "A RuntimeException should be thrown "
                        + "when trying to read from a non-existent file.");
    }

    @Test
    void readFromFile_EmptyFile_Ok() {
        String fileName = "empty.csv";
        List<String> actual = fileReader.readFromFile(RESOURCES_PATH + fileName);
        assertEquals(Collections.emptyList(), actual, "Should return an empty "
                + "list when file is empty.");
    }

    @Test
    void readFromFile_OnlyHeaderPresent_Ok() {
        String fileName = "headerOnly.csv";
        List<String> actual = fileReader.readFromFile(RESOURCES_PATH + fileName);
        assertEquals(Collections.emptyList(), actual, "Should return an empty list when "
                + "file contains only header.");
    }
}
