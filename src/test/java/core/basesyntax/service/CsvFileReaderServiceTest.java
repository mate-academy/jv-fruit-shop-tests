package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileReaderServiceTest {
    private static CsvFileReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new CsvFileReaderServiceImpl();
    }

    @Test
    void readFromFile_validFile_ok() {
        String validFilePath = "src/main/resources/DatabaseOfShop.csv";
        List<String> expectedLines = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actualLines = readerService.readFromFile(validFilePath);
        assertEquals(expectedLines, actualLines);
    }

    @Test
    void readFromFile_invalidFile_notOk() {
        String invalidFilePath = "src/main/DatabaseOfShop.csv";
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(invalidFilePath));
    }

    @Test
    void readFromFile_nullFile_notOk() {
        assertThrows(NullPointerException.class, () -> readerService.readFromFile(null));
    }
}
