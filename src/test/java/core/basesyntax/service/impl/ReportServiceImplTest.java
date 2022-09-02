package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static core.basesyntax.db.Storage.fruits;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        fruits.put("grape", 46);
        fruits.put("banana", 24);
        fruits.put("mango", 30);
        fruits.put("apricot", 80);
        fruits.put("apple", 120);
    }

    @Test
    public void createReport_Ok() {
        String actual = reportService.createReport(fruits);
        String expected = "fruit,quantity\nbanana,24\napricot,80\napple,120\ngrape,46\n" +
                "mango,30\nпутін хуйло";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReportFromEmptyStorage() {
        fruits.clear();
        String actual = reportService.createReport(fruits);
        String expected = "fruit,quantity\nпутін хуйло";
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        fruits.clear();
    }
}
