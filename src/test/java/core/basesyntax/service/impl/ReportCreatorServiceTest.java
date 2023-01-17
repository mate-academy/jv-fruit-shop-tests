package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.ReportCreatorService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static ReportCreatorService reportCreatorService;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl();
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void createReport_fullStorage_ok() {
        fruitDao.saveQuantity("banana", 152);
        fruitDao.saveQuantity("apple", 90);
        String expected = reportCreatorService.createReport();
        String actual = "fruit,quantity" + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90" + System.lineSeparator();
        Assert.assertEquals("Expected report " + expected + ", but was "
                + actual, expected, actual);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        String expected = reportCreatorService.createReport();
        String actual = "fruit,quantity" + System.lineSeparator();
        Assert.assertEquals("Expected report " + expected + ", but was "
                + actual, expected, actual);
    }

    @After
    public void tearDown() {
        fruitDao.getAll().clear();
    }
}
