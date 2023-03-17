package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.implementation.FruitShopReportServiceImpl;
import org.junit.After;
import org.junit.Test;

public class FruitShopReportServiceImplTest {
    public static final String BANANA_KEY = "banana";
    private static final String HEADER = "fruit, quantity";
    private static final String FIRST_ROW = "banana, 152";
    private static final String SECOND_ROW = "apple, 90";
    private static final String REPORT = HEADER + System.lineSeparator()
            + FIRST_ROW + System.lineSeparator() + SECOND_ROW;
    private static final int BANANA_VALUE = 152;
    private static final String APPLE_KEY = "apple";
    private static final int APPLE_VALUE = 90;

    private FruitShopReportService fruitShopReportService = new FruitShopReportServiceImpl();

    @After
    public void clearStorage() {
        FruitStorage.clear();
    }

    @Test
    public void createReport_Ok() {
        FruitStorage.put(BANANA_KEY, BANANA_VALUE);
        FruitStorage.put(APPLE_KEY, APPLE_VALUE);
        String actual = fruitShopReportService.createReport();
        assertEquals(REPORT, actual);
    }

    @Test
    public void createReport_empty_Ok() {
        String actual = fruitShopReportService.createReport();
        assertEquals(HEADER, actual);
    }
}
