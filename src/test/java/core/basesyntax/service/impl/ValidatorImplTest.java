package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.Validator;
import org.junit.Test;

public class ValidatorImplTest {
    private static final Validator VALIDATOR = new ValidatorImpl();
    private static final String FIRST_LETTERS_OF_TYPE_ACTIVITY = "bsrp";

    @Test
    public void validate_Ok() {
        String[] typeActivity = FIRST_LETTERS_OF_TYPE_ACTIVITY.split("");
        for (String activity : typeActivity) {
            String string = activity + ",qwertyasdfgzxcv," + "0123456789";
            assertTrue(VALIDATOR.validate(string));
        }
    }

    @Test (expected = RuntimeException.class)
    public void validateWrongStringFormat_NotOk() {
        String wrongString = "a,qwerty,20";
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
