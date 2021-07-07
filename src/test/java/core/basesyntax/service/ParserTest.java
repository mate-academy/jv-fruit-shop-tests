package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ParserImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parse_validData_OK() {
        String stringToParse = "b,banana,12";
        Transaction expected = new Transaction(Operation.B, new Fruit("banana"), 12);
        Transaction actual = parser.parseLine(stringToParse);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongStringType() {
        parser.parseLine("");
    }

    @Test(expected = RuntimeException.class)
    public void parse_stringWithoutElement() {
        parser.parseLine("b,apple,");
    }

    @Test(expected = RuntimeException.class)
    public void parse_stringWithNegativeQuantity() {
        parser.parseLine("p,orange,-10");
    }
}
