package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.service.ReportCreator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String FIRST_KEY = "banana";
    private static final String SECOND_KEY = "apple";
    private static final String EXPECTED = HEADER
            + System.lineSeparator()
            + FIRST_KEY + ",20"
            + System.lineSeparator()
            + SECOND_KEY + ",80";
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void beforeClass() {
        FruitShopDao fruitShopDao = new FruitShopDaoImpl();
        fruitShopDao.put(FIRST_KEY, 20);
        fruitShopDao.put(SECOND_KEY, 80);
        reportCreator = new ReportCreatorImpl(fruitShopDao);
    }

    @Test
    public void creatReport_Ok() {
        assertEquals(EXPECTED, reportCreator.create());
    }
}
