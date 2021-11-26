package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.TransactionDto;
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
    public void parseLine_ok() {
        String line = "b,apple,45";
        TransactionDto expected = new TransactionDto("b", "apple", 45);
        TransactionDto actual = parser.parseLine(line);
        assertEquals(expected,actual);
    }

    @Test
    public void parseLine_withSpace_ok() {
        String line = "b,green apple,45";
        TransactionDto expected = new TransactionDto("b", "green apple", 45);
        TransactionDto actual = parser.parseLine(line);
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_notValidQuantity_not_Ok() {
        String line = "b,apple, ";
        TransactionDto actual = parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_emptyLine_not_Ok() {
        String line = "   ";
        TransactionDto actual = parser.parseLine(line);
    }
}
