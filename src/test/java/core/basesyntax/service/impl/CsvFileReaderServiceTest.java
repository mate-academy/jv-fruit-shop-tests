package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceTest {
    private static CsvFileReaderService csvFileReaderService;

    @BeforeAll
    static void beforeAll() {
        csvFileReaderService = new CsvFileReaderService();
    }

    @Test
    void readFromFile_correctFilePath_ok() {
        List<String> expectedInputList = new ArrayList<>();
        Path path = Path.of("src/test/resources/input.csv");
        try {
            expectedInputList = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> resultList = csvFileReaderService.readFromFile("src/test/resources/input.csv");
        assertEquals(resultList, expectedInputList);
    }

    @Test
    void readFromFile_incorrectFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvFileReaderService.readFromFile("incorrect/file/path"));
    }

    @Test
    void readFromFile_fileNotExist_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvFileReaderService.readFromFile("src/test/resources/input.csvs"));
    }
}
