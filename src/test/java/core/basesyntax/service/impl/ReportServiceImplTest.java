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
    public static void beforeClass() {
        activityDaoDb = new ActivityDaoDbImpl();
        reportService = new ReportServiceImpl(activityDaoDb);
        fruitFirst = new Fruit("peach");
        fruitSecond = new Fruit("grape");
    }

    @Test
    public void test_empty_report() {
        String report = reportService.makeReport();
        Assert.assertEquals("Must be equals", CSV_HEAD_REPORT + CSV_END_LINE, report);
    }

    @Test
    public void test_create_report() {
        Storage.data.put(fruitFirst, 10);
        Storage.data.put(fruitSecond, 15);
        String report = reportService.makeReport();
        String stringBuilder = CSV_HEAD_REPORT + CSV_END_LINE
                + fruitFirst.getName() + CSV_SEPARATOR + 10 + CSV_END_LINE
                + fruitSecond.getName() + CSV_SEPARATOR + 15;
        Assert.assertEquals("Must be equals", stringBuilder, report);
    }

    @After
    public void clear() {
        Storage.data.clear();
    }
}
