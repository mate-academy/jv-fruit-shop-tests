package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceTest {
    private static CsvFileWriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new CsvFileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validStorageData_ok() {
        String validInputFile = "src/test/resources/ValidInputFile.csv";
        Storage.dataStorage.put("banana", 152);
        Storage.dataStorage.put("apple", 90);
        writerService.writeToFile(validInputFile);
        String actual = readFromFile(validInputFile).trim();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_emptyStorageData_ok() {
        String emptyInputFile = "src/test/resources/EmptyInputFile.csv";
        writerService.writeToFile(emptyInputFile);
        String actual = readFromFile(emptyInputFile).trim();
        String expected = "fruit,quantity";
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.dataStorage.clear();
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
