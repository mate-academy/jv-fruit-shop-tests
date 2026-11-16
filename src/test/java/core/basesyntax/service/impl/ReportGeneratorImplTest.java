package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() {
        Storage.clear();
    }

    @Test
    void getReport_emptyStorage_Ok() {
        String report = reportGenerator.getReport();
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, report);
    }

    @Test
    void getReport_withData_Ok() {
        Storage.add("banana", 20);
        Storage.add("apple", 15);
        String report = reportGenerator.getReport();
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("banana,20"));
        assertTrue(report.contains("apple,15"));
    }
}
