package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReaderServiceImplTest {
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new CsvReaderServiceImpl();
    }

    @Test
    void readFromFile_Ok() {
        String path = "src/test/resources/source.csv";
        List<String> data = readerService.readFromCsvFile(path);
        assertFalse(data.isEmpty());
    }

    @Test
    void incorrectFileName_notOk() {
        String path = "src/test/resources/target2131.csv";
        assertThrows(RuntimeException.class, () -> readerService.readFromCsvFile(path));
    }
}
