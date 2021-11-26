package core.basesyntax.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.Validator;
import org.junit.Test;

public class ValidatorImplTest {
    private final Validator validator = new ValidatorImpl();
    
    @Test
    public void validateLine_validLine_ok() {
        String line = "p,apple,200";
        boolean validationResult = validator.validateLine(line);
        assertTrue(validationResult);
    }
    
    @Test
    public void validateLine_invalidLine_notOk() {
        String line = "qwe,rtyu,uuuuu";
        boolean validationResult = validator.validateLine(line);
        assertFalse(validationResult);
        line = "p,banana";
        validationResult = validator.validateLine(line);
        assertFalse(validationResult);
        line = ",banana,20";
        validationResult = validator.validateLine(line);
        assertFalse(validationResult);
        line = "p,banana,p2p";
        validationResult = validator.validateLine(line);
        assertFalse(validationResult);
        line = "p,banana,";
        validationResult = validator.validateLine(line);
        assertFalse(validationResult);
        line = "P,banana,20";
        validationResult = validator.validateLine(line);
        assertFalse(validationResult);
    }
}
