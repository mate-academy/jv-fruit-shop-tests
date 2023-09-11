package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.CsvReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String RESOURCES_PASS = "src/main/resources/";
    private ReaderService readerService;

    @Test
    void getLines_emptyFile_Ok() {
        readerService = new CsvReaderServiceImpl();
        List<String> result = readerService.getLines(RESOURCES_PASS + "testEmpty.csv");
        assertTrue(result.isEmpty(), "Expected empty list");
    }

    @Test
    void getLines_nonExistingFile_NotOk() {
        readerService = new CsvReaderServiceImpl();
        assertThrows(RuntimeException.class, () -> readerService.getLines(
                RESOURCES_PASS + "nonExisting.csv"));
    }

    @Test
    void getLines_validFile_Ok() {
        readerService = new CsvReaderServiceImpl();
        List<String> result = readerService.getLines(RESOURCES_PASS + "text.csv");
        assertEquals(9, result.size(), "The file has the following number of lines:");
    }
}
