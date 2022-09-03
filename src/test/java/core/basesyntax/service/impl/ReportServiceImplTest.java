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

    @Test
    public void getReport_Ok() {
        Storage.getFruitsMap().put(new Fruit("banana"), 25);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,25" + System.lineSeparator();
        String report = reportService.getReport();
        assertEquals(expected, report);
    }

    @After
    public void tearDown() {
        Storage.getFruitsMap().clear();
    }
}
