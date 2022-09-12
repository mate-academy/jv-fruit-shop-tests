package core.basesyntax.services.impl;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    @Before
    public void setUp() {
        Storage.fruitMap.put("banana", 150);
        Storage.fruitMap.put("apple", 34);
    }

    @After
    public void tearDown() {
        Storage.fruitMap.clear();
    }

    @Test
    public void reportService_createReport_OK() {
        ReportService reportService = new ReportServiceImpl(new FruitStorageDaoImpl());
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,150" + System.lineSeparator() + "apple,34";
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }
}
