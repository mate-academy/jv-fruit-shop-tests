package core.basesyntax.shop;

import core.basesyntax.shop.impl.FruitTransaction;
import core.basesyntax.shop.service.RowParser;
import core.basesyntax.shop.service.RowParserImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RowParserTest {
    private static final String INCORRECT_AMOUNT_LINE = "type,fruit,quantity";
    private static final String INCORRECT_LINE = "!@#$%^&*()_+";
    private static final String EMPTY_LINE = "";
    private static final String ONE_ELEMENT_LINE = "type";
    private static RowParser parser;
    private static final String CORRECT_LINE = "b,pear,5";

    @BeforeClass
    public static void beforeClass() {
        parser = new RowParserImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_nullString_NotOk() {
        parser.parseLine(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_emptyLine_NotOk() {
        parser.parseLine(EMPTY_LINE);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_incorrectAmountLine_NotOk() {
        parser.parseLine(INCORRECT_AMOUNT_LINE);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_incorrectLine_NotOk() {
        parser.parseLine(INCORRECT_LINE);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_oneElementLine_NotOk() {
        parser.parseLine(ONE_ELEMENT_LINE);
    }

    @Test
    public void parseLine_correctLine_Ok() {
        FruitTransaction expected = new FruitTransaction("b", "pear", 5);
        FruitTransaction actual = parser.parseLine(CORRECT_LINE);
        Assert.assertEquals(expected, actual);
    }
}
