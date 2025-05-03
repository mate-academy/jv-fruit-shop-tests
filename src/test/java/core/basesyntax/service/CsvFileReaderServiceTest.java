package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileReaderServiceTest {
    private static CsvFileReaderService readerService;
    private static final String VALID_FILE_PATH = "src/main/resources/DatabaseOfShop.csv";
    private static final String WRONG_FILE_PATH = "src/main/DatabaseOfShop.csv";

    @BeforeAll
    static void beforeAll() {
        readerService = new CsvFileReaderServiceImpl();
    }

    @Test
    void readFromFile_validFile_ok() {
        List<String> expectedLines = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actualLines = readerService.readFromFile(VALID_FILE_PATH);
        assertEquals(expectedLines, actualLines);
    }

    @Test
    void readFromFile_invalidFile_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(WRONG_FILE_PATH));
    }

    @Test
    void readFromFile_nullFile_notOk() {
        assertThrows(NullPointerException.class, () -> readerService.readFromFile(null));
    }
}
