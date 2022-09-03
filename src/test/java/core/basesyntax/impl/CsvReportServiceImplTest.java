package core.basesyntax.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new CsvReportServiceImpl();
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void createReport_CreateFinalReport_Ok() {
        Storage.fruitStorage.put("lemon", 20);
        Storage.fruitStorage.put("apple", 50);
        Storage.fruitStorage.put("banana", 5);
        String actualResult = reportService.createReport(Storage.fruitStorage);
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,5" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "lemon,20";
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void createReport_CreateFinalReportOnlyWithTitleAndWithoutFruits_Ok() {
        String actualResult = reportService.createReport(Storage.fruitStorage);
        String expectedResult = "fruit,quantity";
        Assert.assertEquals(actualResult, expectedResult);
    }
}
