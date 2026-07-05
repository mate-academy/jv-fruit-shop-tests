package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        Storage.clear();
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_emptyStorage_ReturnHeaderOnly() {
        String report = reportGenerator.getReport();
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, report);
    }

    @Test
    void getReport_oneFruit_Ok() {
        Storage.put("banana", 100);
        String report = reportGenerator.getReport();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator();
        assertEquals(expected, report);
    }

    @Test
    void getReport_multipleFruits_Ok() {
        Storage.put("banana", 100);
        Storage.put("apple", 50);
        String report = reportGenerator.getReport();
        assertTrue(report.startsWith("fruit,quantity"));
        assertTrue(report.contains("banana,100"));
        assertTrue(report.contains("apple,50"));
    }
}
