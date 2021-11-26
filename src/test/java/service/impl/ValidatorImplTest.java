package service.impl;

import db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.Validator;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeAll() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_ok() {
        String line = "r,banana,100";
        boolean expected = validator.validate(line);
        Assert.assertTrue(expected);
    }

    @Test(expected = RuntimeException.class)
    public void validate_emptyQuantity_notOk() {
        String line = "r,banana,";
        validator.validate(line);
    }

    @Test(expected = RuntimeException.class)
    public void validate_emptyLine_notOk() {
        String line = " ";
        validator.validate(line);
    }

    @Test(expected = RuntimeException.class)
    public void validate_quantityIsNotNumber_notOk() {
        String line = "r,banana,two";
        validator.validate(line);
    }

    @Test(expected = RuntimeException.class)
    public void validate_negativeQuantity_notOk() {
        String line = "r,banana,-100";
        validator.validate(line);
    }

    @Test(expected = RuntimeException.class)
    public void validate_notValidLengthLine_notOk() {
        String line = "r,banana,100,20";
        validator.validate(line);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
