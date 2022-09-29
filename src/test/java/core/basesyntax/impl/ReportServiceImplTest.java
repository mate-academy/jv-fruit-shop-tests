package core.basesyntax.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportService reportService = new ReportServiceImpl();

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void create_FinalReport_Ok() {
        Storage.fruitStorage.put("kiwi", 20);
        Storage.fruitStorage.put("apple", 50);
        Storage.fruitStorage.put("banana", 5);
        String actualResult = reportService.createReport(Storage.fruitStorage);
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,5" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "kiwi,20";
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void create_EmptyReport_Ok() {
        String actualResult = reportService.createReport(Storage.fruitStorage);
        String expectedResult = "fruit,quantity";
        Assert.assertEquals(actualResult, expectedResult);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}
