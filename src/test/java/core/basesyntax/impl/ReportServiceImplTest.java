package core.basesyntax.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        Storage.fruitStorage.clear();
    }

    @Test
    public void create_FinalReport_Ok() {
        Storage.fruitStorage.put("kiwi", 20);
        Storage.fruitStorage.put("apple", 50);
        Storage.fruitStorage.put("banana", 5);
        String actual = reportService.createReport(Storage.fruitStorage);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,5" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "kiwi,20";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void create_EmptyReport_Ok() {
        String actual = reportService.createReport(Storage.fruitStorage);
        String expected = "fruit,quantity";
        Assert.assertEquals(actual, expected);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}
