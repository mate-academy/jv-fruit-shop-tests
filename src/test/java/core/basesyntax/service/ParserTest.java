package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static final String PURCHASE = "p";
    private static final Fruit BANANA = new Fruit("banana");
    private static Parser parser;

    @BeforeClass
    public static void before() {
        parser = new ParserImpl();
    }

    @Test
    public void validLine_Ok() {
        String lineToParse = "p,banana,2019";
        Transaction expected = new Transaction(PURCHASE, BANANA, 2019);
        Transaction actual = parser.parseLine(lineToParse);
        assertEquals(expected.getFruit(), actual.getFruit());
        assertEquals(expected.getOperation(), actual.getOperation());
        assertEquals(expected.getQuantity(), actual.getQuantity());
    }

    @Test (expected = RuntimeException.class)
    public void invalidLine_NotEnoughElements_Not_Ok() {
        String lineToParse = "p,fd,";
        parser.parseLine(lineToParse);
    }

    @Test (expected = RuntimeException.class)
    public void invalidLine_NotNumber_Not_Ok() {
        String lineToParse = "p,fd,number";
        parser.parseLine(lineToParse);
    }
}
