package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
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
        reportHandler = new FruitStoreReportHandler(storageDao);
    }

    @Test
    public void makeReport_someDataToReport_ok() {
        Storage.getStorage().put("banana", 100);
        Storage.getStorage().put("apple", 150);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,150";
        String actual = reportHandler.makeReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makeReport_emptyStorage_ok() {
        String expected = "fruit,quantity";
        String actual = reportHandler.makeReport();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void makeReport_nullStorageDao_notOk() {
        ReportHandler storageDaoNullHandler = new FruitStoreReportHandler(null);
        storageDaoNullHandler.makeReport();
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
