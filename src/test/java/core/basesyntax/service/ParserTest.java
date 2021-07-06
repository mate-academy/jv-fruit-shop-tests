package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Transaction;
import org.junit.Test;

public class ParserTest {
    @Test
    public void parseLineToTransaction_Ok() {
        Transaction expected = new Transaction("s", "banana", 25);
        Transaction actual = new ParserImpl().parse("s,banana,25");
        assertEquals(expected, actual);
    }
}
