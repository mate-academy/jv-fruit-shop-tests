package core.basesyntax.dao.impl;

import static org.junit.Assert.assertArrayEquals;

import core.basesyntax.dao.ShopParser;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopParserImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static ShopParser shopParser;

    @BeforeClass
    public static void setUp() {
        shopParser = new ShopParserImpl();
    }

    @Test
    public void parse_isOk() {
        String[] expected = {"b,apple,12", "p,banana,45"};
        String[] actual = shopParser.parse("b,apple,12" + LINE_SEPARATOR + "p,banana,45");
        assertArrayEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_nullDataFromFile_notOk() {
        shopParser.parse(null);
    }
}
