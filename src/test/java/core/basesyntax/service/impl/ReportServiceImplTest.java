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
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void report_validData_Ok() {
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");
        Storage.storage.put(banana, 10);
        Storage.storage.put(apple, 20);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,20" + System.lineSeparator();
        String actual = reportService.generateReport();
        Assert.assertTrue("Report is not correct", expected.equals(actual));

    }

    @Test
    public void report_singleLineData_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator();
        String actual = reportService.generateReport();
        Assert.assertTrue("Report is not correct", actual.equals(expected));

    }

    @Test
    public void report_emptyData_NotOk() {
        String expected = "fruit,quantity";
        String actual = reportService.generateReport().trim();
        Assert.assertTrue("Report is not correct", actual.equals(expected));
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
