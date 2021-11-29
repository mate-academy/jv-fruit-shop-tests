package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static final String INCORRECT_AMOUNT_LINE = "type,fruit,quantity";
    private static final String INCORRECT_LINE = "tayp2e!fruit,quantity";
    private static final String EMPTY_LINE = "";
    private static final String ONE_ELEMENT_LINE = "type";
    private static Parser parser;
    private static final String CORRECT_LINE = "s,banana,10";

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserCsvLineService();
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
        Transaction expected = new Transaction(Operation.SUPPLY, new Fruit("banana"), 10);
        Transaction actual = parser.parseLine(CORRECT_LINE);
        Assert.assertEquals(expected, actual);
    }
}
