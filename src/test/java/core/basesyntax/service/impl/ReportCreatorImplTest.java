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
    private static ReportCreator reportCreator;
    private static StorageDao storageDao;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportCreator = new ReportCreatorImpl(storageDao);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void noTransactionsMade_ok() {
        String expected = "fruit,quantity";
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void reportWithTransactions_ok() {
        storageDao.updateValues("banana", 10);
        storageDao.updateValues("strawberry", 10);
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "banana,10" + LINE_SEPARATOR
                + "strawberry,10";
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }
}
