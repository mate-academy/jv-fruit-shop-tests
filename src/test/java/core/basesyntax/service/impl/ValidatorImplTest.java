package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorImplTest {
    private static final Validator validator = new ValidatorImpl();

    @Test
    public void lineIsValid_Ok() {
        String correctLine = "s,apple,100";
        Assert.assertTrue(validator.validate(correctLine));
    }

    @Test(expected = RuntimeException.class)
    public void lineIsValid_notOk() {
        String correctLine = "some line";
        Assert.assertTrue(validator.validate(correctLine));
    }

    @Test(expected = RuntimeException.class)
    public void lineIsNull_Ok() {
        String nullLine = null;
        Assert.assertTrue(validator.validate(nullLine));
    }
}
