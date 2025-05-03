package core.basesyntax.service;

import core.basesyntax.service.impl.ValidatorImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {
    private Validator validator;

    @Before
    public void setUp() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_validData_ok() {
        boolean actual = validator.validate("b,banana,25");
        Assert.assertTrue(actual);
    }

    @Test
    public void validate_invalidOperation_notOk() {
        boolean actual = validator.validate("a,banana,25");
        Assert.assertFalse("Operation is invalid", actual);
    }

    @Test
    public void validate_emptyFruitName_notOk() {
        boolean actual = validator.validate("b,,25");
        Assert.assertFalse("Fruit name is empty", actual);
    }

    @Test
    public void validate_invalidFruitName_notOk() {
        boolean actual = validator.validate("b,$8jl,25");
        Assert.assertFalse("Fruit name is invalid", actual);
    }

    @Test
    public void validate_quantityIsNotNumeric_notOk() {
        boolean actual = validator.validate("b,banana,not");
        Assert.assertFalse("Quantity is not numeric", actual);
    }

    @Test
    public void validate_quantityIsNegative_notOk() {
        boolean actual = validator.validate("b,banana,-10");
        Assert.assertFalse("Quantity is negative", actual);
    }

    @Test
    public void validate_quantityIsEmpty_notOk() {
        boolean actual = validator.validate("b,banana,");
        Assert.assertFalse("Quantity is empty", actual);
    }

    @Test
    public void validate_moreThanThreeParameters_notOk() {
        boolean actual = validator.validate("b,banana,25,40");
        Assert.assertFalse("Incorrect input line, more than 3 parameters", actual);
    }

    @Test(expected = NullPointerException.class)
    public void validate_nullInputValue_notOk() {
        boolean actual = validator.validate(null);
    }
}
