package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.interfaces.ReportService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class ReportServiceImplTest {
    private static final int LIST_SIZE = 3;
    private ReportService reportService;
    private Storage storage;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        storage = Storage.getInstance();
    }

    @BeforeEach
    public void clearStorage() {
        storage.getFruitInventory().clear();
    }

    @Test
    public void testGenerateReport_nonEmptyStorage() {
        storage.getFruitInventory().put("banana", 20);
        storage.getFruitInventory().put("apple", 100);
        List<String> report = reportService.generateReport(storage);
        assertEquals(3, report.size());
        assertEquals("fruit,quantity", report.get(0));
        assertEquals("banana,20", report.get(1));
        assertEquals("apple,100", report.get(2));
    }

    @Test
    public void testGenerateReport_emptyStorage() {
        List<String> report = reportService.generateReport(storage);
        assertEquals(LIST_SIZE, report.size());
        assertEquals("fruit,quantity", report.get(0));
    }
}
