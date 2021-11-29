package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void initializeFields() {
        validator = new ValidatorImpl();
    }

    @Test(expected = RuntimeException.class)
    public void validate_lineIsNotFormatted_throwException() {
        validator.validate("b, banana, 20");
    }

    @Test
    public void validate_requireLineFormat_ok() {
        Assert.assertTrue(validator.validate("b,banana,20"));
    }
}
