package core.basesyntax.basesyntax.service;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ParserImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static final String BANANCHIK = "bananchik";
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        OperationHandler handler = new PurchaseOperationHandler();
        Fruit bananchik = new Fruit(BANANCHIK);
        parser = new ParserImpl();
    }

    @Test
    public void parser_parserLine_Ok() {
        String line = "b,bananchik,500";
        Transaction expected = new Transaction("b", BANANCHIK, 500);
        Transaction actual = parser.parseLine(line);

        Assert.assertEquals(expected.getOperation(), actual.getOperation());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getQuantitiy(), actual.getQuantitiy());
    }

    @Test(expected = RuntimeException.class)
    public void parser_parserLineEmptyElement_Not_Ok() {
        String line = "p,aple";
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


