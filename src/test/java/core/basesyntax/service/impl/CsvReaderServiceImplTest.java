package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvReaderServiceImplTest {
    private static final String SOURCE = "src/test/resources/source.csv";
    private static final String EMPTY_SOURCE = "src/test/resources/empty.csv";
    private final ReaderService readerService = new CsvReaderServiceImpl();

    @Test
    void readFromFile_Ok() {
        List<String> data = readerService.readFromCsvFile(SOURCE);
        assertFalse(data.isEmpty());
    }

    @Test
    void readFromEmptyFile_Ok() {
        List<String> data = readerService.readFromCsvFile(EMPTY_SOURCE);
        assertTrue(data.isEmpty());
    }

    @Test
    void incorrectFileName_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromCsvFile("sadasdwq"));
    }
}
