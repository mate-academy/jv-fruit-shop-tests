package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static Storage storage;
    private static Map<String, Integer> balance;
    private static String expectedReport;

    @BeforeClass
    public static void beforeAll() {
        storage = new Storage();
        reportService = new ReportServiceImpl(storage);
        balance = Map.of("apple", 25, "banana", 15, "pear", 10);
        expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,15" + System.lineSeparator()
                + "apple,25" + System.lineSeparator() + "pear,10";
    }

    @Test
    public void getReport_void_ok() {
        String expected = "";
        String actual = reportService.getReport();
        Assert.assertEquals("Report must be void but it is " + actual, expected, actual);
    }

    @Test
    public void getReport_threeFruitsType_ok() {
        storage.STOCK_BALANCE.putAll(balance);
        String expected = expectedReport;
        String actual = reportService.getReport();
        Assert.assertEquals("Report must be " + expected
                + ", but it is " + actual, expected, actual);
    }

    @After
    public void tearDown() {
        storage.STOCK_BALANCE.clear();
    }
}
