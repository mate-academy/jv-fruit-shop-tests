package core.basesyntax.dao.impl;

import static org.junit.Assert.assertArrayEquals;

import core.basesyntax.dao.ShopActivitiesParser;
import core.basesyntax.db.Storage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopActivitiesParserImplTest {
    private static final String END_LINE = System.lineSeparator();
    private static ShopActivitiesParser shopActivitiesParser;
    private String stringToSplit;

    @BeforeClass
    public static void setUp() {
        shopActivitiesParser = new ShopActivitiesParserImpl();
    }

    @Before
    public void init() {
        stringToSplit = "b,apple,12" + END_LINE + "p,banana,45";
    }

    @Test
    public void parseActivities_Ok() {
        String[] expected = {"b,apple,12", "p,banana,45"};
        String[] actual = shopActivitiesParser.parseActivities(stringToSplit);
        assertArrayEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseActivities_NullDataFromFile_NotOk() {
        shopActivitiesParser.parseActivities(null);
    }

    @AfterClass
    public static void clear() {
        Storage.fruits.clear();
    }
}
