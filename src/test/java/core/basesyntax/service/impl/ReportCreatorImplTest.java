package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportCreator = new ReportCreatorImpl(storageDao);
    }

    @Test
    public void report_Ok() {
        Storage.dataBase.put(new Fruit("apple"), 10);
        Storage.dataBase.put(new Fruit("orange"), 67);
        Storage.dataBase.put(new Fruit("banana"), 14);
        String actual = reportCreator.createReport();
        String expected = "fruit,quantity\nbanana,14"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator()
                + "orange,67"
                + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void report_NotOk() {
        Storage.dataBase.put(new Fruit("orange"), 67);
        Storage.dataBase.put(null, 67);
        Storage.dataBase.put(new Fruit("banana"), null);
        String actual = reportCreator.createReport();
        String expected = "fruit,quantity\norange,67" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
