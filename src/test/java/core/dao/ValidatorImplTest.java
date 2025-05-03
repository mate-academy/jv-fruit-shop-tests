package core.dao;

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
    public void isDataValid_Ok() {
        boolean actual = validator.validate("r,apple,100");
        Assert.assertTrue(actual);
    }

    @Test
    public void isAmountPositive_Ok() {
        boolean actual = validator.validate("r,apple,50");
        Assert.assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isDataValidWithAmountNegative_NotOk() {
        String inputData = "r,apple,-50";
        validator.validate(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void isValidNullData_NotOk() {
        validator.validate(null);
    }

    @Test(expected = RuntimeException.class)
    public void isValidDataWithShortLength_NotOk() {
        String inputData = "r,apple";
        validator.validate(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void isValidDataWithLongLength_NotOk() {
        String inputData = "r,apple,10,test,300";
        validator.validate(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void isValidDataWithEmptyData_NotOk() {
        String inputData = "";
        validator.validate(inputData);
    }
}
