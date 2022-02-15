package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.Validator;
import org.junit.Test;

public class ValidatorImplTest {
    private static final Validator validator = new ValidatorImpl();

    @Test
    public void validate_Ok() {
        String[] typeActivity = {"b","s","r","p"};
        for (String activity : typeActivity) {
            String string = activity + ",text," + "0123456789";
            assertTrue(validator.validate(string));
        }
    }

    @Test (expected = RuntimeException.class)
    public void validateWrongData_NotOk() {
        String incorrectData = "s,98cucumber, five";
        validator.validate(incorrectData);
    }

    @Test (expected = NullPointerException.class)
    public void validateNullData_NotOk() {
        validator.validate(null);
    }

    @Test (expected = RuntimeException.class)
    public void validateEmptyString_NotOk() {
        validator.validate("");
    }
}
