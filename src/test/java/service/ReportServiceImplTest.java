package service;

import static org.junit.Assert.assertEquals;

import database.StorageFruits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shop.Fruit;

public class ReportServiceImplTest {
    private ReportService reportService;

    @Before
    public void setUp() {
        StorageFruits.storageFruits.clear();
        reportService = new ReportServiceImpl();
    }

    @After
    public void tearDown() {
        StorageFruits.storageFruits.clear();
    }

    @Test
    public void reportServiceEmptyStorage_Ok() {
        assertEquals("fruit,quantity", reportService.makeReport());
    }

    @Test
    public void reportServiceCorrectStorage_Ok() {
        StorageFruits.storageFruits.put(new Fruit("banana"), 152);
        StorageFruits.storageFruits.put(new Fruit("apple"), 90);
        StorageFruits.storageFruits.put(new Fruit("lemon"), 1);
        StorageFruits.storageFruits.put(new Fruit("plum"), 29);
        StorageFruits.storageFruits.put(new Fruit("pineapple"), 32);
        String expected = "fruit,quantity\n" + "banana,"
                + 152 + "\n" + "apple," + 90 + "\n" + "lemon,"
                + 1 + "\n" + "plum," + 29 + "\n" + "pineapple," + 32;
        String actual = reportService.makeReport();
        assertEquals(expected,actual);
    }
}
