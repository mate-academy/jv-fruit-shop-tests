package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static FruitShopDao fruitShopDao;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        fruitShopDao = new FruitShopDaoImpl();
        reportService = new ReportServiceImpl(fruitShopDao);
    }

    @Test
    public void reportService_Ok() {
        Storage.fruitStorage.put("apple", 10);
        Storage.fruitStorage.put("banana", 20);
        Storage.fruitStorage.put("orange", 30);
        String actual = reportService.createReport();
        String expected = HEADER + System.lineSeparator()
                + "banana," + 20 + System.lineSeparator()
                + "orange," + 30 + System.lineSeparator()
                + "apple," + 10 + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reportService_createForEmptyStorage_Ok() {
        String actual = reportService.createReport();
        String expected = HEADER + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}
