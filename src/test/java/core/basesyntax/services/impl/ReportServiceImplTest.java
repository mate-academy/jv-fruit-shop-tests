package core.basesyntax.services.impl;

import static junit.framework.TestCase.fail;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {
    private final ReportService reportService = new ReportServiceImpl(new FruitStorageDaoImpl());

    @After
    public void tearDown() {
        Storage.fruitMap.clear();
    }

    @Test
    public void reportService_createReport_OK() {
        Storage.fruitMap.put("banana", 150);
        Storage.fruitMap.put("apple", 34);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,150" + System.lineSeparator() + "apple,34";
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void reportService_emptyStorage_notOK() {
        reportService.createReport();
        fail("We need inform user about empty storage and throw RuntimeException");
    }
}
