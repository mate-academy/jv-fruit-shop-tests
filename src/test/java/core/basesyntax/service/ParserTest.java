package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Parser parserImpl;

    @BeforeClass
    public static void start() {
        parserImpl = new ParserImpl();
    }

    @Test
    public void test_parseLine_ok() {
        Fruit apple = new Fruit();
        String inputLine = "b,apple,10";
        apple.setName("apple");
        Transaction expected = new Transaction(Operation.B, apple, 10);
        Transaction actual = parserImpl.parseLine(inputLine);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void test_parseLine_notOk() {
        parserImpl.parseLine("114546");
    }
}

