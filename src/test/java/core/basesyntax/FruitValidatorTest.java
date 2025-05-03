package core.basesyntax;

import core.basesyntax.service.Validator;
import core.basesyntax.service.impl.FruitValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitValidatorTest {
    private static Validator fruitValidator;

    @BeforeClass
    public static void beforeClass() {
        fruitValidator = new FruitValidator();
    }

    @Test
    public void validator_validateWithCorrectInput_Ok() {
        String[] parsedLineArrayOk = new String[]{"b", "banana", "33"};
        Assert.assertTrue(fruitValidator.validate(parsedLineArrayOk));
    }

    @Test
    public void validator_validateWithBadOperator_Ok() {
        String[] parsedLineArrayOk = new String[]{"q", "banana", "33"};
        Assert.assertFalse(fruitValidator.validate(parsedLineArrayOk));
    }

    @Test
    public void validator_validateWithEmptyFruit_Ok() {
        String[] parsedLineArrayOk = new String[]{"b", "", "33"};
        Assert.assertFalse(fruitValidator.validate(parsedLineArrayOk));
    }

    @Test
    public void validator_validateWithNegativeQuantity_Ok() {
        String[] parsedLineArrayOk = new String[]{"b", "apple", "-4"};
        Assert.assertFalse(fruitValidator.validate(parsedLineArrayOk));
    }

    @Test
    public void validator_validateWithTwoFields_Ok() {
        String[] parsedLineArrayOk = new String[]{"b", "apple"};
        Assert.assertFalse(fruitValidator.validate(parsedLineArrayOk));
    }
}
