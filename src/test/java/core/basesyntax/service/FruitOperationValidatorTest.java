package core.basesyntax.service;

import core.basesyntax.service.inter.Validator;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitOperationValidatorTest {
    private static Validator<String> validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        validator = new FruitOperationValidator();
    }

    @Test
    public void validate_ValidData_Ok() throws RuntimeException {
        validator.validate("b,banana,20");
    }

    @Test(expected = RuntimeException.class)
    public void validate_IncorrectLength_NotOk() throws RuntimeException {
        validator.validate("banana,10");
    }

    @Test(expected = RuntimeException.class)
    public void validate_IncorrectQuantity_NotOk() throws RuntimeException {
        validator.validate("b,banana,-5");
    }

    @Test(expected = RuntimeException.class)
    public void validate_Null_NotOk() throws RuntimeException {
        validator.validate(null);
    }

    @Test(expected = RuntimeException.class)
    public void validate_EmptyLine_NotOk() throws RuntimeException {
        validator.validate("");
    }
}
