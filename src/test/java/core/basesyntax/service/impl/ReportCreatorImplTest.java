package core.basesyntax.service.impl;

import core.basesyntax.dao.ShopDao;
import core.basesyntax.dao.ShopDaoImpl;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String NOTATION = "fruit, quantity";
    private static final int FIRST_RECORD_INDEX = 0;
    private static ReportCreator reportCreator;
    private static ShopDao shopDao;

    @BeforeClass
    public static void beforeClass() {
        reportCreator = new ReportCreatorImpl();
        shopDao = new ShopDaoImpl();
    }

    @Test
    public void createReport_Ok() {
        shopDao.add("lemon", 30);
        String expected = "fruit, quantity" + System.lineSeparator() + "lemon,30";
        String actual = reportCreator.createReport();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void correctNotation_Ok() {
        String[] splitReport = reportCreator.createReport().split(System.lineSeparator());
        boolean actual = splitReport[FIRST_RECORD_INDEX].equals(NOTATION);
        Assert.assertTrue(actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}
