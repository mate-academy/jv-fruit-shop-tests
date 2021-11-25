package service.impl;

import bd.LocalStorage;
import java.util.ArrayList;
import java.util.List;
import model.Fruit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReportService;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
        expected = new ArrayList<>();
    }

    @Test
    public void reportService_validWork_ok() {
        LocalStorage.fruits.add(new Fruit("apple", 123));
        LocalStorage.fruits.add(new Fruit("banana", 1));
        expected.add("fruit,quantity");
        expected.add("apple,123");
        expected.add("banana,1");
        Assert.assertEquals(expected, reportService.createReport());
        LocalStorage.fruits.clear();
    }

    @Test
    public void reportService_emptyStorage_ok() {
        expected.add("fruit,quantity");
        Assert.assertEquals(expected, reportService.createReport());
        LocalStorage.fruits.clear();
    }

    @After
    public void tearDown() {
        expected.clear();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        LocalStorage.fruits.clear();
    }
}
