package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_ok() {
        String[] operationType = {"b", "s", "r", "p"};
        for (String activity : operationType) {
            String string = activity + ",text," + 1234;
            Assert.assertTrue(validator.validate(string));
        }
    }

    @Test
    public void validate_notOk() {
        String[] operationType = {"b", "s", "r", "p"};
        for (String activity : operationType) {
            String string = activity + ",text," + 1234;
            Assert.assertFalse(!Pattern.matches("[bpsr],[a-z]+,[0-9]+", string));
        }
    }

    @Test (expected = RuntimeException.class)
    public void validateEmptyString_NotOk() {
        validator.validate("");
    }
}
