package service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;
import storage.Storage;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.getFruitDbResult().clear();
    }

    @Test
    void getReport_returnsFormattedCsvReport() {
        Storage.getFruitDbResult().put("banana", 25);
        Storage.getFruitDbResult().put("apple", 44);

        String report = reportGenerator.getReport();

        String expected = String.join(System.lineSeparator(),
                "fruit,quantity",
                          "banana,25",
                          "apple,44");

        assertEquals(expected, report);
    }

    @Test
    void getReport_emptyStorageReturnsHeader() {
        String report = reportGenerator.getReport();

        assertEquals("fruit,quantity", report);
    }
}
