package service.impl;

import static org.junit.Assert.assertEquals;

import model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReportService;
import storage.Storage;

public class ReportServiceImplTest {
    private static Fruit banana;
    private static Fruit apple;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        banana = new Fruit("banana");
        apple = new Fruit("apple");
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.put(banana, 100);
        Storage.storage.put(apple, 200);
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
