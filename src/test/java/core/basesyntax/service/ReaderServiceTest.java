package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.CsvReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String SOURCE = "src/test/resources/input.csv";
    private static final String EMPTY_SOURCE = "src/test/resources/empty.csv";
    private static final String FIRST_READ_LINE_SAMPLE = "    type,fruit,quantity";
    private ReaderService readerService = new CsvReaderServiceImpl();

    @Test
    void readFromFile_ok() {
        List<String> strings = readerService.readFromFile(SOURCE);
        assertFalse(strings.isEmpty());
    }

    @Test
    void readEmptyFile_ok() {
        List<String> strings = readerService.readFromFile(EMPTY_SOURCE);
        assertTrue(strings.isEmpty());
    }

    @Test
    void incorrectFileName_notOk() {
        assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile("notExistFileName");
        });
    }

    @Test
    void correctDataCatchFirstLine_Ok() {
        List<String> strings = readerService.readFromFile(SOURCE);
        assertEquals(FIRST_READ_LINE_SAMPLE, strings.get(0));
    }

    @Test
    void readFromNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(null);
        });
    }
}
