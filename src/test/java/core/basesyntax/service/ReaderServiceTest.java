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
        readerService = new CsvReaderServiceImpl(RESOURCES_PASS + "testEmpty.csv");
        List<String> result = readerService.getLines();
        assertTrue(result.isEmpty(), "Expected empty list");
    }

    @Test
    void getLines_nonExistingFile_NotOk() {
        readerService = new CsvReaderServiceImpl(RESOURCES_PASS + "nonExisting.csv");
        assertThrows(RuntimeException.class, () -> readerService.getLines());
    }

    @Test
    void getLines_validFile_Ok() {
        readerService = new CsvReaderServiceImpl(RESOURCES_PASS + "text.csv");
        List<String> result = readerService.getLines();
        assertEquals(9, result.size(), "The file has the following number of lines:");
    }
}
