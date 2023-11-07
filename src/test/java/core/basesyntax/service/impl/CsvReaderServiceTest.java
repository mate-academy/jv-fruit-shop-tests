package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReaderServiceTest {
    private static final String VALID_FILE_PATH = "src/test/resources/test.csv";
    private static final String INVALID_FILE_PATH = "src/test/test.csv";

    private ReaderService reader;

    @BeforeEach
    void setUp() {
        reader = new CsvReaderService();
    }

    @Test
    void readFromExistingFile_Ok() {
        String firstLine = "type, fruit, quantity";
        String fruitOne = "b, banana, 100";
        String fruitTwo = "b, apple, 100";
        List<String> expected = new ArrayList<>();
        expected.add(firstLine);
        expected.add(fruitOne);
        expected.add(fruitTwo);
        List<String> actual = reader.readFromFile(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void readFromNullFile_NotOk() {
        assertThrows(NullPointerException.class, () -> reader.readFromFile(null));
    }

    @Test
    void readFromNonExistingFile_NotOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromFile(INVALID_FILE_PATH));
    }
}
