package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import core.basesyntax.service.impl.ParserImpl;
import org.junit.Test;


public class ParserTest {
    Parser parser = new ParserImpl();

    @Test
    public void test_ParsingString_OK() {
        String strToParse = "b,banana,12";
        Transaction expected = new Transaction(Operation.B, new Fruit("banana"), 12);
        Transaction actual = parser.parseLine(strToParse);
        assertEquals(expected, actual);
    }

    @Test
    public void test_NullString_Not_OK() {
        assertThrows(RuntimeException.class,
                () -> parser.parseLine(null));
    }

    @Test
    public void test_WrongStringType() {
        assertThrows(RuntimeException.class,
                () -> parser.parseLine(""));
    }
}
