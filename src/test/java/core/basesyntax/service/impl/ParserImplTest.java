package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import core.basesyntax.service.Validator;
import org.junit.Test;

public class ParserImplTest {
    private final Validator validator = new ValidatorImpl();
    private final Parser parser = new ParserImpl();
    
    @Test
    public void parseLine_validLine_ok() {
        String line = "s,banana,100";
        TransactionDto expected = new TransactionDto("s", "banana", 100);
        TransactionDto actual = (TransactionDto) parser.parseLine(line, validator);
        assertEquals(expected, actual);
    }
    
    @Test
    public void parseLine_invalidLine_notOk() {
        String line = "123,abc,456";
        TransactionDto expected = null;
        TransactionDto actual = (TransactionDto) parser.parseLine(line, validator);
        assertEquals(expected, actual);
    }
}
