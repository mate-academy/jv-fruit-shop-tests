package core.basesyntax.basesyntax.service;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ParserImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static final String PINEAPPLE = "pineapple";
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parser_parserLine_Ok() {
        String line = "b,pineapple,500";
        Transaction expected = new Transaction("b", PINEAPPLE, 500);
        Transaction actual = parser.parseLine(line);

        Assert.assertEquals(expected.getOperation(), actual.getOperation());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getQuantitiy(), actual.getQuantitiy());
    }

    @Test(expected = RuntimeException.class)
    public void parser_parserLineEmptyElement_Not_Ok() {
        String line = "p,apple";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parser_parserLineInvalidNumber_Not_Ok() {
        String line = "p,apple,three";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parser_parserLineInvalidHandler_Not_Ok() {
        String line = "f,apple,three";
        parser.parseLine(line);
    }
}


