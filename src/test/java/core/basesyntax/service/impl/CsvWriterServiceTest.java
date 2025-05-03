package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvWriterServiceTest {
    private CsvWriterService csvWriterService;

    @BeforeEach
    void setUp() {
        csvWriterService = new CsvWriterService();
    }

    @Test
    void write_file_ok() {
        csvWriterService.writeToCsv("blabla", "src/main/resources/output.csv");
    }

    @Test
    void write_nullContent_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvWriterService.writeToCsv(null, "invalid/path/output.csv"));
    }

    @Test
    void write_nullBoth_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvWriterService.writeToCsv(null, null));
    }

    @Test
    void write_emptyContent_ok() {
        csvWriterService.writeToCsv("","src/main/resources/output.csv");
    }

    @Test
    void write_customOutputPath_ok() {
        String customPath = "src/test/resources/custom_output.csv";
        csvWriterService.writeToCsv("content", customPath);
    }
}
