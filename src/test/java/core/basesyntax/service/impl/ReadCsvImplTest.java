package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReadCsvService;
import exception.FileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadCsvImplTest {
    private static final String PATH_TO_FILE = "src/main/resources/balance.csv";
    private static final String NOT_EXISTING_FILE_PATH =
            "src/main/service/not_existing_file.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/main/resources/empty.csv";
    private static ReadCsvService readCsvService;
    private static List<String> expectedFileContent = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        readCsvService = new ReadCsvImpl();
        try {
            expectedFileContent = Files.readAllLines(Path.of(PATH_TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException("path to file is not correct: " + PATH_TO_FILE);
        }
    }

    @Test
    void read_Ok() {
        List<String> actual = readCsvService.readFromFile(PATH_TO_FILE);
        assertEquals(expectedFileContent, actual);
    }

    @Test
    void readFromEmptyFile_NotOk() {
        final List<String> actual = readCsvService.readFromFile(PATH_TO_EMPTY_FILE);
        final List<String> expected = readCsvService.readFromFile(PATH_TO_FILE);
        assertNotEquals(actual, expected);
    }

    @Test
    void readFromWrongPath_NotOk() {
        assertThrows(FileException.class,
                () -> readCsvService.readFromFile(NOT_EXISTING_FILE_PATH),
                "Could not read file from this path");
    }
}
