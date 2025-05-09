package fruitshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.db.Storage;
import fruitshop.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_nonEmptyStorage_ok() {
        Storage.put("apple", 50);
        Storage.put("banana", 30);

        String actualReport = reportGenerator.getReport();
        StringBuilder expectedReport = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,30").append(System.lineSeparator())
                .append("apple,50");
        assertEquals(expectedReport.toString(), actualReport);
    }

    @Test
    void getReport_emptyStorage_headerOnly() {
        String report = reportGenerator.getReport();
        assertEquals("fruit,quantity", report);
    }

    @AfterEach
    void clearStorage() {
        Storage.clear();
    }
}
