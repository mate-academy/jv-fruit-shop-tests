package service.impl;

import static org.junit.Assert.assertEquals;

import model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReportService;
import storage.Storage;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("banana"), 100);
        Storage.storage.put(new Fruit("apple"), 200);
    }

    @Test
    public void getReport_isOk() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,200"
                + System.lineSeparator();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
