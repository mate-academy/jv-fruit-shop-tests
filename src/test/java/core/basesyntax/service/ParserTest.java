package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ParserImpl;
import org.junit.Test;

public class ParserTest {
    private final Parser parser = new ParserImpl();

    @Test
    public void test_ParsingString_OK() {
        String strToParse = "b,banana,12";
        Transaction expected = new Transaction(Operation.B, new Fruit("banana"), 12);
        Transaction actual = parser.parseLine(strToParse);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void test_NullString_Not_OK() {
        parser.parseLine(null);
    }

    @Test(expected = RuntimeException.class)
    public void test_WrongStringType() {
        parser.parseLine("");
    }
}
