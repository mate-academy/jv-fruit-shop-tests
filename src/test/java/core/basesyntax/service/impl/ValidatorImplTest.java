package core.basesyntax.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.Validator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test(expected = RuntimeException.class)
    public void checkNullString_Ok() {
        validator.isLineValid(null);
    }

    @Test
    public void checkEmptyString_Ok() {
        assertFalse(validator.isLineValid(""));
    }

    @Test
    public void checkIncorrectString_Ok() {
        assertFalse(validator.isLineValid("ggg"));
    }

    @Test
    public void checkCorrectString_Ok() {
        assertTrue(validator.isLineValid("p,banana,13"));
    }
}
