package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static StorageDao storageDao;
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportCreator = new ReportCreatorImpl(storageDao);
    }

    @Test
    public void createReport_ok() {
        storageDao.update("orange", 10);
        storageDao.update("lemon", 10);
        storageDao.update("lime", 20);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "orange,10"
                + System.lineSeparator()
                + "lemon,10"
                + System.lineSeparator()
                + "lime,20";
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
