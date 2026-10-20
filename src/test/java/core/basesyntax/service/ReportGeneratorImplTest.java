package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void getReport_multipleFruits_ok() {
        Storage.fruits.put("banana", 100);
        Storage.fruits.put("apple", 50);

        String report = reportGenerator.getReport();

        assertTrue(report.contains("banana,100"));
        assertTrue(report.contains("apple,50"));
    }

    @Test
    void getReport_validStorage_ok() {
        Storage.fruits.put("banana", 100);

        String report = reportGenerator.getReport();

        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("banana,100"));
    }

    @Test
    void getReport_emptyStorage_ok() {
        String report = reportGenerator.getReport();

        assertEquals("fruit,quantity", report);
    }
}
