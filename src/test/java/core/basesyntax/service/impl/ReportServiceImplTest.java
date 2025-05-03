package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void setUp() {
        Storage.getFruits().clear();
    }

    @Test
    public void createEmptyReport_Ok() {
        String report = reportService.getReport();
        assertEquals("fruit,quantity", report);
    }

    @Test
    public void createReport_Ok() {
        Storage.getFruits().put(new Fruit("apple"), 10);
        Storage.getFruits().put(new Fruit("banana"), 30);
        String report = reportService.getReport();
        assertEquals("fruit,quantity\n" + "banana,30\n" + "apple,10", report);
    }
}
