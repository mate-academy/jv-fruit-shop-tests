package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Validator;
import org.junit.Test;

public class ValidatorImplTest {
    private final Validator validator = new ValidatorImpl();
    
    @Test
    public void validateLine_validLine_ok() {
        boolean expected = true;
        String line = "p,apple,200";
        boolean actual = validator.validateLine(line);
        assertEquals(expected, actual);
    }
    
    @Test
    public void validateLine_invalidLine_notOk() {
        boolean expected = false;
        String line = "qwe,rtyu,uuuuu";
        boolean actual = validator.validateLine(line);
        assertEquals(expected, actual);
    }
}
