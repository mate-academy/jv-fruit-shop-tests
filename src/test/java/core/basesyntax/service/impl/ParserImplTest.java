package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import org.junit.Test;

public class ParserImplTest {
    private final Parser<TransactionDto> parser = new ParserImpl();
    
    @Test
    public void parseLine_validLine_ok() {
        String line = "s,banana,100";
        TransactionDto expected = new TransactionDto("s", "banana", 100);
        TransactionDto actual = parser.parseLine(line);
        assertEquals(expected, actual);
        line = "b,apple,20";
        expected = new TransactionDto("b", "apple", 20);
        actual = (TransactionDto) parser.parseLine(line);
        assertEquals(expected, actual);
        line = "p,orange,200";
        expected = new TransactionDto("p", "orange", 200);
        actual = (TransactionDto) parser.parseLine(line);
        assertEquals(expected, actual);
        line = "r,pineapple,500";
        expected = new TransactionDto("r", "pineapple", 500);
        actual = (TransactionDto) parser.parseLine(line);
        assertEquals(expected, actual);
    }
}
