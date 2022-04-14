package core.basesyntax;

import core.basesyntax.fruitshop.validator.Validator;
import core.basesyntax.fruitshop.validator.ValidatorImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {
    private static Validator validator;

    @Before
    public void beforeAll() {
        validator = new ValidatorImpl();

    }

    @Test
    public void validateData_IsOk() {
        String[] data = {"s", "banana", "100"};
        Assert.assertTrue(validator.validateTransaction(data));
    }

    @Test(expected = RuntimeException.class)
    public void validateSpecificData_IncorrectDataLength_NotOk() {
        String[] data = {"p", "banana"};
        validator.validateTransaction(data);
    }

    @Test(expected = RuntimeException.class)
    public void validateSpecificData_IncorrectQuantityOfFruit_NotOk() {
        String[] data = {"b", "banana", "-100"};
        validator.validateTransaction(data);
    }

    @Test(expected = RuntimeException.class)
    public void validateSpecificData_IncorrectOperation_NotOk() {
        String[] data = {"t", "banana", "100"};
        validator.validateTransaction(data);
    }
}
