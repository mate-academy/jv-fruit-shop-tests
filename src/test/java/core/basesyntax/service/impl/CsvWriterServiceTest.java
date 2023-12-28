package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvWriterServiceTest {
    private CsvWriterService csvWriterService;
    private CsvReaderService csvReaderService;

    @BeforeEach
    void setUp() {
        csvWriterService = new CsvWriterService();
        csvReaderService = new CsvReaderService();
    }

    @Test
    void write_file_ok() {
        String content = "blabla";
        String outputPath = "src/main/resources/output.csv";
        csvWriterService.writeToCsv(content, outputPath);
        String actualContent = String.valueOf(csvReaderService.readFromFile(outputPath));
        assertNotNull(actualContent);
        assertFalse(actualContent.isEmpty());
        assertEquals(content, actualContent);
    }

    @Test
    void write_nullContent_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> csvWriterService.writeToCsv(null, "invalid/path/output.csv"));
    }

    @Test
    void write_nullBoth_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> csvWriterService.writeToCsv(null, null));
    }

    @Test
    void write_emptyContent_ok() {
        String outputPath = "src/main/resources/output.csv";
        csvWriterService.writeToCsv("", outputPath);
        String actualContent = String.valueOf(csvReaderService.readFromFile(outputPath));
        assertNotNull(actualContent);
        assertTrue(actualContent.isEmpty());
    }

    @Test
    void write_customOutputPath_ok() {
        String content = "content";
        String customPath = "src/test/resources/custom_output.csv";
        csvWriterService.writeToCsv(content, customPath);
        String actualContent = String.valueOf(csvReaderService.readFromFile(customPath));
        assertNotNull(actualContent);
        assertFalse(actualContent.isEmpty());
        assertEquals(content, actualContent);
    }
}
