package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
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
        String fileContent = String.valueOf(csvReaderService
                .readFromFile("src/main/resources/input.csv"));
        Assertions.assertNotNull(fileContent);
        Assertions.assertFalse(fileContent.isEmpty());
    }

    @Test
    void read_file_notOk() {
        assertThrows(RuntimeException.class,
                () -> {
                    String fileContent = String.valueOf(csvReaderService
                            .readFromFile("invalid/path/input.csv"));
                    Assertions.assertNotNull(fileContent);
                    Assertions.assertFalse(fileContent.isEmpty());
                });
    }

    @Test
    void read_nullFilePath_notOk() {
        assertThrows(IllegalArgumentException.class, () -> csvReaderService.readFromFile(null));
    }

    @Test
    void read_emptyFilePath_notOk() {
        assertThrows(IllegalArgumentException.class, () -> csvReaderService.readFromFile(""));
    }
}
