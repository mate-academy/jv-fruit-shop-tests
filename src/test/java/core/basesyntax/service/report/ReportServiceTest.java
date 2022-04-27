package core.basesyntax.service.report;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_ValidCase_OK() {
        storageDao.add("banana", 18);
        storageDao.add("apple", 14);
        storageDao.add("default", 0);
        String actual = reportService.createReport(storageDao.getAll());
        String expected = "fruit,quantity\n"
                + "banana,18" + System.lineSeparator()
                + "apple,14" + System.lineSeparator()
                + "default,0" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        Storage.dataBase.clear();
    }
}

