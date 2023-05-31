package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void reportServiceInitialization() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void create_report_ok() {
        Storage.fruitList.add(new Fruit("banana", 20));
        Storage.fruitList.add(new Fruit("apple", 10));
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,10";
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        Storage.fruitList.clear();
    }
}
