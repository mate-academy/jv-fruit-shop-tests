package core.basesyntax.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.ValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void setup() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_checkValidatorWithProperValues_OK() {
        String[] values = new String[]{"b", "banana", "100"};
        assertTrue(validator.validate(values));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithNegativeQuantity_NotOK() {
        String[] values = new String[]{"b", "banana", "-100"};
        assertFalse(validator.validate(values));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithNull_NotOK() {
        assertTrue(validator.validate(null));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithNullActivityIdentifier_NotOK() {
        String[] values = new String[]{null, "banana", "100"};
        assertTrue(validator.validate(values));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithNullFruitName_NotOK() {
        String[] values = new String[]{"b", null, "100"};
        assertTrue(validator.validate(values));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithNullQuantity_NotOK() {
        String[] values = new String[]{"b", "banana", null};
        assertTrue(validator.validate(values));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithWrongFruitName_NotOK() {
        String[] values = new String[]{"b", "grape", "100"};
        assertTrue(validator.validate(values));
    }
}
