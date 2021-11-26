package core.basesyntax.service.implementation;

import core.basesyntax.service.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorImplTest {
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
        Assert.assertFalse("Invalid operation", actual);
    }

    @Test
    public void validate_emptyFruitName_notOk() {
        boolean actual = validator.validate("b,,25");
        Assert.assertFalse("Fruit name is empty", actual);
    }

    @Test
    public void validate_invalidFruitName_notOk() {
        boolean actual = validator.validate("b,@@@@,25");
        Assert.assertFalse("Invalid fruit name", actual);
    }

    @Test
    public void validate_invalidFruitQuantity_notOk() {
        boolean actual = validator.validate("b,banana,not");
        Assert.assertFalse("Invalid quantity", actual);
    }

    @Test
    public void validate_NegativeQuantity_notOk() {
        boolean actual = validator.validate("b,banana,-10");
        Assert.assertFalse("Quantity is negative", actual);
    }

    @Test
    public void validate_EmptyQuantity_notOk() {
        boolean actual = validator.validate("b,banana,");
        Assert.assertFalse("Quantity is empty", actual);
    }

    @Test
    public void validate_moreThanThreeParameters_notOk() {
        boolean actual = validator.validate("b,banana,25,40");
        Assert.assertFalse("Incorrect input line, more than 3 parameters", actual);
    }
}
