package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.Validator;
import org.junit.Test;

public class ValidatorImplTest {
    private static final Validator VALIDATOR = new ValidatorImpl();

    @Test
    public void validate_Ok() {
        String[] typeActivity = {"b","s","r","p"};
        for (String activity : typeActivity) {
            String string = activity + ",qwertyasdfgzxcv," + "0123456789";
            assertTrue(VALIDATOR.validate(string));
        }
    }

    @Test (expected = RuntimeException.class)
    public void validateWrongStringFormat_NotOk() {
        String wrongString = "b,qwerty,20r";
        VALIDATOR.validate(wrongString);
    }

    @Test (expected = RuntimeException.class)
    public void stringIsNull_NotOk() {
        VALIDATOR.validate(null);
    }

    @Test (expected = RuntimeException.class)
    public void stringIsEmpty() {
        VALIDATOR.validate("");
    }
}
