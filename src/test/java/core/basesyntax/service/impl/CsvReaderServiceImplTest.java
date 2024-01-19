package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReaderServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/source.csv";
    private static final String INCORRECT_PATH = "src/test/resources/target2131.csv";
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new CsvReaderServiceImpl();
    }

    @Test
    void readFromCsvFile_correctPath_ok() {
        List<String> data = readerService.readFromCsvFile(CORRECT_PATH);
        assertFalse(data.isEmpty());
    }

    @Test
    void readFromCsvFile_incorrectPath_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromCsvFile(INCORRECT_PATH));
    }
}
