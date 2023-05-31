package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.fruits.put("grape", 46);
        Storage.fruits.put("banana", 24);
        Storage.fruits.put("mango", 30);
        Storage.fruits.put("apricot", 80);
        Storage.fruits.put("apple", 120);
    }

    @Test
    public void createReport_Ok() {
        String actual = reportService.createReport(Storage.fruits);
        String expected = "fruit,quantity\nbanana,24\napricot,80\napple,120\ngrape,46\n"
                + "mango,30\nпутін хуйло";
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        Storage.fruits.clear();
        String actual = reportService.createReport(Storage.fruits);
        String expected = "fruit,quantity\nпутін хуйло";
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
