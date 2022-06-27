package basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.db.Storage;
import basesyntax.service.ReportCreateService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreateServiceImplTest {
    private static final String TITLE = "fruit,quantity";
    private static ReportCreateService reportCreateService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportCreateService = new ReportCreateServiceImpl(storageDao);
    }

    @Test
    public void createReport_ok() {
        storageDao.updateData("banana", 20);
        storageDao.updateData("apple", 15);
        String expected = TITLE + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,15";
        String actual = reportCreateService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_withoutOperation_ok() {
        String actual = reportCreateService.createReport();
        assertEquals(TITLE, actual);
    }

    @After
    public void tearDown() {
        Storage.storageMap.clear();
    }
}
