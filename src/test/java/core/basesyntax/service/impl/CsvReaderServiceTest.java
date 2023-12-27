package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReaderServiceTest {
    private CsvReaderService csvReaderService;

    @BeforeEach
    void setUp() {
        csvReaderService = new CsvReaderService();
    }

    @Test
    void read_file_ok() {
        csvReaderService.readFromFile("src/main/resources/input.csv");
    }

    @Test
    void read_file_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvReaderService.readFromFile("invalid/path/input.csv"));
    }

    @Test
    void read_nullFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> csvReaderService.readFromFile(null));
    }

    @Test
    void read_emptyFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> csvReaderService.readFromFile(""));
    }
}
