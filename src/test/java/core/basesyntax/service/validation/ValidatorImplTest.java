package core.basesyntax.service.validation;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static String[] validData;
    private static String[] invalidData;
    private static Validator validator;
    private boolean actual;

    @BeforeClass
    public static void beforeClass() {
        validData = new String[]{"b", "banana", "1000"};
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_correctData_ok() {
        actual = validator.validate(validData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void validate_incorrectDataWithNegativeNumber_NotOk() {
        invalidData = new String[]{"b", "avocado", "-10"};
        actual = validator.validate(invalidData);
    }

    @Test(expected = RuntimeException.class)
    public void validate_dataWithoutFirstElement_NotOk() {
        invalidData = new String[]{"apple", "10"};
        actual = validator.validate(invalidData);
    }

    @Test(expected = RuntimeException.class)
    public void validate_invalidDataWithRedundantElement_NotOk() {
        invalidData = new String[]{"b", "banana", "1000", "500"};
        actual = validator.validate(invalidData);
    }

    @Test(expected = RuntimeException.class)
    public void validate_invalidDataWithIntegerElements_NotOk() {
        invalidData = new String[]{"10", "10", "10"};
        actual = validator.validate(invalidData);
    }
}
