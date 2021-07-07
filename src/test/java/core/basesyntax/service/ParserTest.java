package core.basesyntax.service;

import core.basesyntax.dto.ShopOperation;
import core.basesyntax.service.impl.ParserImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {
    private static final String CORRECT_LINE = "p,banana,13";
    private static final String INCORRECT_AMOUNT_LINE = "type,fruit,quantity";
    private static final String INCORRECT_LINE = "&Ufdj5##a,4fas23-12v%#v";
    private static final String EMPTY_LINE = "";
    private static final String ONE_ELEMENT_LINE = "type";
    private Parser parser;

    @Before
    public void setUp() {
        parser = new ParserImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_nullString_NotOk() {
        parser.parseLine(null);
    }

    @Test
    public void parseLine_correctLine_Ok() {
        ShopOperation expected = new ShopOperation("p", "banana", 13);
        ShopOperation actual = parser.parseLine(CORRECT_LINE);
        Assert.assertEquals(expected, actual);
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
}
