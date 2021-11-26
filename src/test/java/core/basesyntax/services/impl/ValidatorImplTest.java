
package core.basesyntax.services.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import core.basesyntax.services.Validator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_validLine_Ok() {
        assertTrue(validator.validate("b,apple,20"));
    }

    @Test
    public void validate_shortFruitName_Ok() {
        assertTrue(validator.validate("b,a,20"));
    }

    @Test
    public void validate_notDigitalOperation_NotOk() {
        assertFalse(validator.validate("0,apple,20"));
    }

    @Test
    public void validate_noOperation_NotOk() {
        assertFalse(validator.validate(",apple,20"));
    }

    @Test
    public void validate_noFruitName_NotOk() {
        assertFalse(validator.validate("b,,20"));
    }

    @Test
    public void validate_noQuantity_NotOk() {
        assertFalse(validator.validate("b,apple,"));
    }

    @Test
    public void validate_noDelimiter_NotOk() {
        assertFalse(validator.validate("bapple20"));
        assertFalse(validator.validate("bapple,20"));
        assertFalse(validator.validate("b,apple20"));
    }

    @Test
    public void validate_emptyLine_NotOk() {
        assertFalse(validator.validate(""));
    }

    @Test
    public void validate_null_NotOk() {
        assertFalse(validator.validate(null));
    }

    @Test
    public void validate_negativeQuantity_NotOk() {
        assertFalse(validator.validate("b,apple,-1"));
    }

    @Test
    public void validate_additionalSymbols_NotOk() {
        assertFalse(validator.validate("b,apple,10 "));
        assertFalse(validator.validate(" b,apple,10"));
        assertFalse(validator.validate("b,apple,10 // 123"));
    }
}
