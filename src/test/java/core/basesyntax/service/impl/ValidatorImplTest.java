package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_correctInput_ok() {
        boolean actual = validator.validate("b,orange,1");
        boolean expected = true;
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void validate_incorrectOperation_notOk() {
        validator.validate("supply,apple,20");
    }

    @Test (expected = RuntimeException.class)
    public void validate_incorrectFruitName_notOk() {
        validator.validate("p,p1neapple,10");
    }

    @Test (expected = RuntimeException.class)
    public void validate_incorrectQuantity_notOk() {
        validator.validate("r,banana,-1");
    }
}
