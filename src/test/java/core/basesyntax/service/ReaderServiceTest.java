package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.CsvReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String RESOURCES_PASS = "src/test/resources/";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new CsvReaderServiceImpl();
    }

    @Test
    void getLines_emptyFile_Ok() {
        List<String> result = readerService.getLines(RESOURCES_PASS + "testEmpty.csv");
        assertTrue(result.isEmpty(), "Expected empty list");
    }

    @Test
    void getLines_nonExistingFile_NotOk() {
        assertThrows(RuntimeException.class, () -> readerService.getLines(
                RESOURCES_PASS + "nonExisting.csv"));
    }

    @Test
    void getLines_validFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> result = readerService.getLines(RESOURCES_PASS + "text.csv");
        assertEquals(9, result.size(), "The file has the following number of lines:");
        assertTrue(expected.containsAll(result));
    }
}
