package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileWriterServiceImplTest {
    private static CsvFileWriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new CsvFileWriterServiceImpl();
    }

    @Test
    void writeToFile_validStorageData_ok() throws IOException {
        String validFile = "src/main/resources/ValidInputFile.csv";
        Storage.FRUITS.put("banana", 152);
        Storage.FRUITS.put("apple", 90);
        String expected = "fruit,quantity\nbanana,152\napple,90";
        writerService.writeToFile(validFile, expected);
        String actual = Files.readString(Path.of(validFile)).trim();
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_emptyReport_ok() throws IOException {
        String invalidFile = "src/main/resources/EmptyFile.csv";
        String emptyReport = "";
        writerService.writeToFile(invalidFile, emptyReport);
        Path path = Path.of(invalidFile);
        assertTrue(Files.exists(path), "File should exist");
        assertTrue(Files.size(path) == 0, "File should be empty");
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
