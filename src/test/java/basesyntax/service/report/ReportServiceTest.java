package basesyntax.service.report;

import static org.junit.Assert.assertEquals;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.storage.Storage;
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
        storageDao.addToDataBase("banana", 18);
        storageDao.addToDataBase("apple", 14);
        storageDao.addToDataBase("default", 0);
        String actual = reportService.createReport(storageDao.getDataBase());
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

