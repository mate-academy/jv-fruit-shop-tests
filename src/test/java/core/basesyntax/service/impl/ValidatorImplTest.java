package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

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
        assertEquals(false, validator.isLineValid(""));
    }

    @Test
    public void checkIncorrectString_Ok() {
        assertEquals(false, validator.isLineValid("ggg"));
    }

    @Test
    public void checkCorrectString_Ok() {
        assertEquals(true, validator.isLineValid("p,banana,13"));
    }
}
