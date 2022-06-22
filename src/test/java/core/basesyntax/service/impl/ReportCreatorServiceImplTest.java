package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.db.Shop;
import core.basesyntax.service.ReportCreatorService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static ReportCreatorService reportCreator;
    private static ShopDao shopDao;

    @BeforeClass
    public static void beforeClass() {
        shopDao = new ShopDaoImpl();
        reportCreator = new ReportCreatorServiceImpl(shopDao);
    }

    @Test
    public void createReportAll_ok() {
        shopDao.add("banana", 5);
        shopDao.add("apple", 15);
        String expected = "fruit, quantity" + LINE_SEPARATOR
                + "banana, 5" + LINE_SEPARATOR
                + "apple, 15";
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_withoutTransactions_ok() {
        String expected = "fruit, quantity";
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void tearDown() {
        Shop.fruits.clear();
    }
}
