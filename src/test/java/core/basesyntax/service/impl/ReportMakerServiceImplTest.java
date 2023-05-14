package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportMakerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportMakerServiceImplTest {
    private ReportMakerService reportMakerService = new ReportMakerServiceImpl();

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void makeReport_shouldWorkWithEmptyStorage_ok() {
        String expectedSTitlesRow = "fruit,quantity" + System.lineSeparator();
        String result = reportMakerService.makeReport();
        Assert.assertEquals("should return only titles", expectedSTitlesRow, result);
    }

    @Test
    public void makeReport_shouldWorkWithFilledStorage_ok() {
        String expectedSTitlesRow = "fruit,quantity" + System.lineSeparator();
        String expectedFirstRow = "apple,100" + System.lineSeparator();
        String expectedSecondRow = "pear,200" + System.lineSeparator();
        String expectedFullResult = expectedSTitlesRow + expectedFirstRow + expectedSecondRow;
        Storage.fruits.put("apple", 100);
        Storage.fruits.put("pear", 200);
        String result = reportMakerService.makeReport();
        Assert.assertEquals("should return only titles", expectedFullResult, result);
    }
}
