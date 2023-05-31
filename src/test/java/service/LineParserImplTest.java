package service;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import shop.Fruit;
import shop.FruitShopOperation;

public class LineParserImplTest {
    private static LineParser lineParser;

    @BeforeClass
    public static void start() {
        lineParser = new LineParserImpl();
    }

    @Test
    public void correctLine_Ok() {
        FruitShopOperation expected = new FruitShopOperation("b",new Fruit("banana"),20);
        String validInput = "b,banana,20";
        FruitShopOperation actual = lineParser.parseLine(validInput);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void lineStartWithNumber_NotOk() {
        String invalidInput = "20,banana,b";
        FruitShopOperation actual = lineParser.parseLine(invalidInput);
    }
}
