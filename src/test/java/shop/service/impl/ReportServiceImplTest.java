package shop.service.impl;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.dao.FruitDao;
import shop.dao.FruitDaoImpl;
import shop.db.DataStorage;
import shop.model.Fruit;
import shop.service.ReportService;

public class ReportServiceImplTest {
    private static FruitDao fruitDao;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
    }

    @AfterClass
    public static void afterAll() {
        DataStorage.storage.clear();
    }

    @Test
    public void reportService_makeReport_ok() {
        Fruit fruit = new Fruit("Cherry", 30);
        Fruit fruit2 = new Fruit("Cocoa", 60);
        DataStorage.storage.add(fruit);
        DataStorage.storage.add(fruit2);
        List<String> reportCheck = reportService.makeReport();
        Assert.assertTrue(reportCheck.contains("fruit,quantity"));
        Assert.assertTrue(reportCheck.contains("Cocoa,60"));
        Assert.assertTrue(reportCheck.contains("Cherry,30"));
    }
}
