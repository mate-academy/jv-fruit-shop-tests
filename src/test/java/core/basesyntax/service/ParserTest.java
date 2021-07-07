package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Test;

public class ParserTest {
    private final Parser parserImpl = new ParserImpl();
    private final Fruit apple = new Fruit();

    @Test
    public void parseLine_Ok() {
        String inputLine = "b,apple,10";
        apple.setName("apple");
        Transaction expected = new Transaction(Operation.B, apple, 10);
        Transaction actual = parserImpl.parseLine(inputLine);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void test_parseLine_Not_OK() {
        parserImpl.parseLine("114546");
    }
}

