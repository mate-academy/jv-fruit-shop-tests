package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReadCsvService;
import exception.FileException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadCsvImplTest {
    private static final String PATH_TO_FILE = "src/main/resources/balance.csv";
    private static final String WRONG_PATH_TO_FILE = "src/main/service/balance.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/main/resources/empty.csv";
    private ReadCsvService readCsvService;
    private List<String> expectedFileContent = new ArrayList<>();

    @BeforeEach
    void setUp() {
        readCsvService = new ReadCsvImpl();
        expectedFileContent = readCsvService.readFromFile(PATH_TO_FILE);
    }

    @Test
    void read_Ok() {
        List<String> actual = readCsvService.readFromFile(PATH_TO_FILE);
        assertEquals(expectedFileContent, actual);
    }

    @Test
    void readNull_NotOk() {
        assertNull(null, "File don`t have any data");
    }

    @Test
    void readFromEmptyFile_NotOk() {
        final List<String> actual = readCsvService.readFromFile(PATH_TO_EMPTY_FILE);
        final List<String> expected = new ArrayList<>();
        assertEquals(actual, expected);
    }

    @Test
    void readFromWrongPath_NotOk() {
        assertThrows(FileException.class,
                () -> readCsvService.readFromFile(WRONG_PATH_TO_FILE),
                "Could not read file from this path");
    }
}
