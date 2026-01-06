package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Inventory;
import core.basesyntax.service.ReportGenerator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        inventory = new Inventory();
    }

    @Test
    public void getReportAlwaysStartsWithHeader() {
        List<String> report = reportGenerator.getReport(inventory);
        assertNotNull(report);
        assertFalse(report.isEmpty());
        assertEquals("fruit, quantity", report.get(0));
    }

    @Test
    public void getReportContainsAllFruitsFromInventory() {
        inventory.setFruitQuantity("apple", 10);
        inventory.setFruitQuantity("banana", 3);
        List<String> report = reportGenerator.getReport(inventory);
        assertEquals(3, report.size());
        assertEquals("fruit, quantity", report.get(0));
        assertTrue(report.contains("apple,10"));
        assertTrue(report.contains("banana,3"));
    }

    @Test
    public void getReportWhenInventoryIsEmptyReturnsOnlyHeader() {
        List<String> report = reportGenerator.getReport(inventory);
        assertEquals(1, report.size());
        assertEquals("fruit, quantity", report.get(0));
    }
}
