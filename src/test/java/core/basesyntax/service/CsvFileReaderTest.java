package core.basesyntax.service;

import core.basesyntax.impl.CsvFileReader;

import java.nio.file.FileSystems;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {
    private static final String VALID_PATH = "src/test/resources/validFile.csv";
    private static final String INVALID_PATH = "src/test/resources/notAFile.csv";
    private static final String EMPTY_PATH = "";
    private static FileReaderService fileReaderService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new CsvFileReader();
    }

    @Test
    void readFromFile_validPath_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReaderService.readFromFile(VALID_PATH);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readFromFile_nullPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(null));
    }

    @Test
    void readFromFile_emptyPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(EMPTY_PATH));
    }

    @Test
    void readFromFile_invalidPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(INVALID_PATH));
    }
}
