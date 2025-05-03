package core.basesyntax.service.parser.validator;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.ValidationException;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitOperationDtoValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new FruitOperationDtoValidator();
    }

    @Test
    public void validate_ValidData_Ok() throws ValidationException {
        assertTrue(validator.validate("b,banana,20"));
    }

    @Test(expected = ValidationException.class)
    public void validate_IncorrectLength_NotOk() throws ValidationException {
        validator.validate("banana,10");
    }

    @Test(expected = ValidationException.class)
    public void validate_IncorrectQuantity_NotOk() throws ValidationException {
        validator.validate("b,banana,-5");
    }

    @Test(expected = ValidationException.class)
    public void validate_Null_NotOk() throws ValidationException {
        validator.validate(null);
    }

    @Test(expected = ValidationException.class)
    public void validate_EmptyLine_NotOk() throws ValidationException {
        assertTrue(validator.validate(""));
    }
}
