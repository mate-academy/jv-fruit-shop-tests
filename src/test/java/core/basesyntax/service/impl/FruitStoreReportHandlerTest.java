package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.ReportHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStoreReportHandlerTest {
    private static StorageDao storageDao;
    private static ReportHandler reportHandler;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @After
    public void tearDown() {
        storageDao.getAll().clear();
    }

    @Test
    public void makeReport_someDataToReport_ok() {
        storageDao.add("banana", 100);
        storageDao.add("apple", 150);
        reportHandler = new FruitStoreReportHandler(storageDao);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,150";
        String actual = reportHandler.makeReport();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void makeReport_nullStorageDao_notOk() {
        reportHandler = new FruitStoreReportHandler(null);
        reportHandler.makeReport();
    }

    @Test
    public void makeReport_emptyStorage_ok() {
        reportHandler = new FruitStoreReportHandler(storageDao);
        String expected = "fruit,quantity";
        String actual = reportHandler.makeReport();
        Assert.assertEquals(expected, actual);
    }
}
