package core.basesyntax.shop.service.impl;

import static core.basesyntax.shop.db.FruitShopStorage.getAll;

import core.basesyntax.shop.dao.FruitShopDaoImpl;
import core.basesyntax.shop.model.Fruit;
import core.basesyntax.shop.service.ReportService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    @BeforeClass
    public static void fillStorageBeforeTest() {
        getAll().put(new Fruit("peach"), 125);
        getAll().put(new Fruit("pineapple"), 30);
        getAll().put(new Fruit("rambutan"), 90);
        getAll().put(new Fruit("jackfruit"), 80);
    }

    @AfterClass
    public static void afterClass() {
        getAll().clear();
    }

    @Test
    public void collect_makeReportFromStorageData_ok() {
        ReportService reportService = new ReportServiceImpl(new FruitShopDaoImpl());
        String expected = "fruit,quantity\n"
                + "rambutan,90\n"
                + "pineapple,30\n"
                + "peach,125\n"
                + "jackfruit,80";
        String actual = reportService.collect();
        Assert.assertEquals(expected, actual);
    }
}
