package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportService = new ReportServiceImpl(storageDao);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }

    @Test
    public void report_Ok() {
        Storage.dataBase.put(new Fruit("apple"), 10);
        Storage.dataBase.put(new Fruit("orange"), 67);
        Storage.dataBase.put(new Fruit("banana"), 14);
        String actual = reportService.report();
        String expected = "fruit,quantity\nbanana,14\r\napple,10\r\norange,67\r\n";
        assertEquals(expected, actual);
    }

    @Test
    public void report_NotOk() {
        Storage.dataBase.put(new Fruit("apple"), 10);
        Storage.dataBase.put(null, 67);
        Storage.dataBase.put(new Fruit("banana"), null);
        String actual = reportService.report();
        String expected = "fruit,quantity\napple,10\r\n";
        assertEquals(expected, actual);
    }
}
