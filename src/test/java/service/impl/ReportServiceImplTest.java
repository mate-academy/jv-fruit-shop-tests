package service.impl;

import dao.FruitDao;
import dao.impl.FruitDaoImpl;
import database.Storage;
import model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.ReportService;

public class ReportServiceImplTest {
    private FruitDao fruitDao;
    private ReportService reportService;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
        Storage.fruits.clear();
    }

    @Test
    public void report_valid_ok() {
        Storage.fruits.put(new Fruit("banana"), 152);
        Storage.fruits.put(new Fruit("apple"), 23);
        Storage.fruits.put(new Fruit("kiwi"), 62);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,23"
                + System.lineSeparator()
                + "kiwi,62";
        Assert.assertEquals(expected, reportService.createReport());
    }
}
