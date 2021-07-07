package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Transaction;
import core.basesyntax.service.impl.LineValidatorImpl;
import core.basesyntax.service.impl.ParserImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static LineValidator lineValidator;
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        lineValidator = new LineValidatorImpl();
        parser = new ParserImpl(lineValidator);
    }

    @Test
    public void parser_correctWork_ok() {
        String line = "b,banana,20";
        Transaction actual = new Transaction("b","banana",20);
        Transaction expected = parser.parseLine(line);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parser_withOutOperation_notOk() {
        String line = "banana,20";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parser_withOutFruit_notOk() {
        String line = "b,,20";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parser_withOutQuantity_notOk() {
        String line = "b,banana,";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parser_emptyLine_notOk() {
        String line = "";
        parser.parseLine(line);
    }

    @Test(expected = NullPointerException.class)
    public void parser_Null_notOk() {
        String line = null;
        parser.parseLine(line);
    }
}
