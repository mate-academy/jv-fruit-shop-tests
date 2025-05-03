package core.basesyntax.service.data;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static String[] validData;
    private static String[] invalidData;
    private static String[] dataWithNegativeNumber;
    private static Validator validator;
    private boolean actual;

    @BeforeClass
    public static void beforeClass() {
        validData = new String[]{"b", "banana", "1000"};
        invalidData = new String[]{"apple", "10"};
        dataWithNegativeNumber = new String[]{"b", "avocado", "-10"};
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_correctData_ok() {
        actual = validator.validator(validData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void validate_incorrectDataWithNegativeNumber_notOk() {
        actual = validator.validator(dataWithNegativeNumber);
    }

    @Test(expected = RuntimeException.class)
    public void validate_DataWithoutFirstElement_notOk() {
        actual = validator.validator(invalidData);
    }
}
