package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static ParserCsvLineService parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserCsvLineService();
    }

    @Test
    public void parseValidLine_Ok() {
        Transaction expected = new Transaction(Operation.BALANCE, new Fruit("banana"), 25);
        Transaction actual = parser.parseLine("b,banana,25");
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidLine_NotOk() {
        parser.parseLine("a,56,");
    }

    @Test(expected = NullPointerException.class)
    public void parseNullLine_NotOk() {
        parser.parseLine(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseLineEmptyLine_NotOk() {
        parser.parseLine("");
    }

    @Test(expected = RuntimeException.class)
    public void parseLineOneElementLine_NotOk() {
        parser.parseLine("type");
    }
}
