package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.impl.ReportServiceImpl;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitStorage.clear();
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_CreateFinalReport_Ok() {
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
    public void createReport_CreateEmptyReport_Ok() {
        String actualResult = reportService.createReport(Storage.fruitStorage);
        String expectedResult = "fruit,quantity";
        Assert.assertEquals(actualResult, expectedResult);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}
