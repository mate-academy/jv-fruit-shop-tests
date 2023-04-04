package core.basesyntax.services;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {
    private final ReportService reportService = new ReportServiceImpl();

    @After
    public void afterEachTest() {
        Storage.products.clear();
    }

    @Test
    public void getReport_Ok() {
        Storage.products.add(new Product("banana", 100));
        Storage.products.add(new Product("apple", 50));
        String expected = "name,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,50";
        String actual = reportService.createReport(Storage.products);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getReport_NotOk() {
        Storage.products.add(new Product("banana", 500));
        Storage.products.add(new Product("apple", 100));
        String expectReport = "name,quantity" + System.lineSeparator()
                + "banana,250" + System.lineSeparator()
                + "apple,475";
        String actualReport = reportService.createReport(Storage.products);
        boolean actual = expectReport.equals(actualReport);
        Assert.assertFalse(actual);
    }

    @Test
    public void getReport_noFruit_Ok() {
        String actual = reportService.createReport(Storage.products);
        Assert.assertNull(actual);
    }
}
