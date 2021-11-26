package core.basesyntax.service.implementation;

import core.basesyntax.service.Validator;
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
    public void validate_negativeQuantity_notOk() {
        boolean actual = validator.validate("b,banana,-8");
        Assert.assertFalse("Quantity is negative", actual);
    }

    @Test
    public void validate_emptyQuantity_notOk() {
        boolean actual = validator.validate("b,banana,");
        Assert.assertFalse("Quantity is empty", actual);
    }

    @Test
    public void validate_moreThanThreeParameters_notOk() {
        boolean actual = validator.validate("b,banana,28,potato,32");
        Assert.assertFalse("Incorrect input line, more than 3 parameters", actual);
    }
}
