package core.basesyntax.service.impl;

import core.basesyntax.dao.ActivityDaoDb;
import core.basesyntax.dao.ActivityDaoDbImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String CSV_SEPARATOR = ",";
    private static final String CSV_HEAD_REPORT = "fruit,quantity";
    private static final String CSV_END_LINE = System.lineSeparator();
    private static ActivityDaoDb activityDaoDb;
    private static Fruit fruitFirst;
    private static Fruit fruitSecond;
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        activityDaoDb = new ActivityDaoDbImpl();
        reportService = new ReportServiceImpl(activityDaoDb);
        fruitFirst = new Fruit("peach");
        fruitSecond = new Fruit("grape");
    }

    @Test
    public void test_make_report_empty_storage_ok() {
        String expected = CSV_HEAD_REPORT + CSV_END_LINE;
        String actual = reportService.makeReport();
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void test_make_report_ok() {
        Storage.data.put(fruitFirst, 10);
        Storage.data.put(fruitSecond, 15);
        String actual = reportService.makeReport();
        String expected = CSV_HEAD_REPORT + CSV_END_LINE
                + fruitFirst.getName() + CSV_SEPARATOR + 10 + CSV_END_LINE
                + fruitSecond.getName() + CSV_SEPARATOR + 15;
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @After
    public void clearUp() {
        Storage.data.clear();
    }
}
