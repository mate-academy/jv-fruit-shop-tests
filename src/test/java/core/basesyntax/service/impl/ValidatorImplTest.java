package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorImplTest {
    private static final Validator validator = new ValidatorImpl();

    @Test
    public void validate_ok() {
        String[] operationType = {"b", "s", "r", "p"};
        for (String activity : operationType) {
            String string = activity + ",text," + 1234;
            Assert.assertTrue(validator.validate(string));
        }
    }

    @Test (expected = RuntimeException.class)
    public void validateEmptyString_NotOk() {
        validator.validate("");
    }
}
