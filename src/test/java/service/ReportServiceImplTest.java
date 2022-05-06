package service;

import static org.junit.Assert.assertEquals;

import dao.StorageDaoImpl;
import db.Storage;
import model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl(new StorageDaoImpl());
    }

    @Test
    public void createReport_correctData_Ok() {
        Storage.dataBase.put(new Fruit("apple"), 10);
        Storage.dataBase.put(new Fruit("orange"), 67);
        Storage.dataBase.put(new Fruit("banana"), 14);
        String actual = reportService.createReport();
        String expected = new StringBuilder("fruit,quantity\nbanana,14")
                .append(System.lineSeparator())
                .append("apple,10")
                .append(System.lineSeparator())
                .append("orange,67")
                .append(System.lineSeparator()).toString();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
