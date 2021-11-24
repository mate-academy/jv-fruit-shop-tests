package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        reportService = new ReportServiceImpl(fruitDao);
    }

    @Test
    public void getReport_ok() {
        fruitDao.update(new Fruit("banana"), 92);
        fruitDao.update(new Fruit("banana"), 60);
        fruitDao.update(new Fruit("apple"), 50);
        fruitDao.update(new Fruit("apple"), 40);
        String actual = reportService.getReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }
}
