package core.basesyntax.service.impl;

import core.basesyntax.ValidationException;
import org.junit.Test;

public class FruitOpValidatorTest {
    private FruitOperatorValidator fruitOpValidator = new FruitOperatorValidator();

    @Test(expected = ValidationException.class)
    public void validate_validationException_notOk() throws ValidationException {
        fruitOpValidator.validate(null);
    }

    @Test
    public void validate_correctData_Ok() throws ValidationException {
        fruitOpValidator.validate("b,banana,10");
    }

    @Test(expected = ValidationException.class)
    public void validate_incorrectDataLength_Ok() throws ValidationException {
        fruitOpValidator.validate("b,banana,10,+");
    }

    @Test(expected = ValidationException.class)
    public void validate_negativeAmount_Ok() throws ValidationException {
        fruitOpValidator.validate("b,banana,-10");
    }
}
