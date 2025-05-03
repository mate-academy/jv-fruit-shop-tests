package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void initialization() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void clearStorage() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void formReport_storageIsNotEmpty_ok() {
        Storage.fruitStorage.put(new Fruit("banana"), 20);
        Storage.fruitStorage.put(new Fruit("apple"), 133);
        Storage.fruitStorage.put(new Fruit("orange"), 56);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,133" + System.lineSeparator()
                + "orange,56" + System.lineSeparator();
        String actual = reportService.formReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void formReport_storageIsEmpty_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportService.formReport();
        Assert.assertEquals(expected, actual);
    }
}
