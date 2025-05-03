package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String BANANA_KEY = "banana";
    private static final String APPLE_KEY = "apple";
    private static final String EXPECTED = HEADER
            + System.lineSeparator()
            + BANANA_KEY + ",20"
            + System.lineSeparator()
            + APPLE_KEY + ",80";
    private static ReportCreator reportCreator;

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void creatReport_Ok() {
        FruitShopDao fruitShopDao = new FruitShopDaoImpl();
        fruitShopDao.put(BANANA_KEY, 20);
        fruitShopDao.put(APPLE_KEY, 80);
        reportCreator = new ReportCreatorImpl(fruitShopDao);
        assertEquals(EXPECTED, reportCreator.create());
    }

    @Test
    public void emptyReport_Ok() {
        assertEquals(HEADER, reportCreator.create());
    }

    @Test
    public void creatReport_NotOk() {
        reportCreator = new ReportCreatorImpl(null);
        assertThrows(RuntimeException.class, () -> reportCreator.create());
    }
}
