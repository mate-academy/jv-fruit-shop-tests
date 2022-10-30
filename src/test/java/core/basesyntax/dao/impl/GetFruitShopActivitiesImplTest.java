package core.basesyntax.dao.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.GetFruitShopActivities;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GetFruitShopActivitiesImplTest {
    private static final String END_LINE = System.lineSeparator();
    private static GetFruitShopActivities getFruitShopActivities;
    private String stringToSplit;

    @BeforeClass
    public static void setUp() {
        getFruitShopActivities = new GetFruitShopActivitiesImpl();
    }

    @Before
    public void init() {
        stringToSplit = "b,apple,12" + END_LINE + "p,banana,45";
    }

    @Test
    public void getActivities_Ok() {
        String[] expected = {"b,apple,12", "p,banana,45"};
        String[] actual = getFruitShopActivities.getActivities(stringToSplit);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void getActivities_NullDataFromFile_NotOk() {
        assertThrows(RuntimeException.class, () -> getFruitShopActivities.getActivities(null));
    }

    @AfterClass
    public static void clear() {
        Storage.fruits.clear();
    }
}
