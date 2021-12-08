package core.basesyntax.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.exceptions.LineParseException;
import core.basesyntax.exceptions.NegativeValueException;
import core.basesyntax.exceptions.NullValueException;
import core.basesyntax.services.impl.LineValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class LineValidatorTest {
    private static LineValidator lineValidator;

    @BeforeClass
    public static void setup() {
        lineValidator = new LineValidatorImpl();
    }

    @Test
    public void validate_checkValidatorWithProperValues_OK() {
        String line = "b,banana,100";
        assertTrue(lineValidator.validate(line));
    }

    @Test(expected = NegativeValueException.class)
    public void validate_checkValidatorWithNegativeQuantity_NotOK() {
        String line = "b,banana,-100";
        assertFalse(lineValidator.validate(line));
    }

    @Test(expected = LineParseException.class)
    public void validate_checkValidatorWithNull_NotOK() {
        assertTrue(lineValidator.validate(null));
    }

    @Test(expected = NullValueException.class)
    public void validate_checkValidatorWithNullActivityIdentifier_NotOK() {
        String line = " ,banana,100";
        assertTrue(lineValidator.validate(line));
    }

    @Test(expected = NullValueException.class)
    public void validate_checkValidatorWithNullFruitName_NotOK() {
        String line = "b, ,100";
        assertTrue(lineValidator.validate(line));
    }

    @Test(expected = NullValueException.class)
    public void validate_checkValidatorWithNullQuantity_NotOK() {
        String line = "b,banana, ";
        assertTrue(lineValidator.validate(line));
    }

    @Test(expected = LineParseException.class)
    public void validate_checkValidatorWithWrongFruitName_NotOK() {
        String line = "b,grape,100";
        assertTrue(lineValidator.validate(line));
    }
}
