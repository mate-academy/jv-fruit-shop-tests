package core.basesyntax.service.validator;

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
    public void validate_correctRecord_Ok() {
        String[] fruitRecord = {"b", "banana", "13"};
        Assert.assertTrue(validator.validate(fruitRecord));
    }

    @Test(expected = RuntimeException.class)
    public void validate_WithOneElement_NotOk() {
        String[] fruitRecord = {"r"};
        validator.validate(fruitRecord);
    }

    @Test(expected = RuntimeException.class)
    public void validate_WithEmptyFruit_NotOk() {
        String[] fruitRecord = {"r", "", "12"};
        validator.validate(fruitRecord);
    }

    @Test(expected = RuntimeException.class)
    public void validate_WithNegativeAmount_NotOk() {
        String[] fruitRecord = {"r", "banana", "-12"};
        validator.validate(fruitRecord);
    }
}
