package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_correctData_ok() {
        Storage.stock.put(new Fruit("banana"), 100);
        Storage.stock.put(new Fruit("apple"), 100);
        Assert.assertEquals("Report is not correct.",
                "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,100"
                + System.lineSeparator(), reportService.createReport());
    }

    @Test
    public void createReport_emptyStorage_ok() {
        Assert.assertEquals("Method should handle empty storage.",
                "fruit,quantity" + System.lineSeparator(), reportService.createReport());
    }

    @After
    public void tearDown() {
        Storage.stock.clear();
    }
}
