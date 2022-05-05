package service;

import static org.junit.Assert.assertEquals;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.Fruit;
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

    @Test
    public void report_ok() {
        Storage.dataBase.put(new Fruit("apple"), 10);
        Storage.dataBase.put(new Fruit("orange"), 67);
        Storage.dataBase.put(new Fruit("banana"), 14);
        String actual = reportService.createReport();
        String expected = "fruit,quantity\nbanana,14"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator()
                + "orange,67"
                + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
