package core.basesyntax.validate;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ValidationSubtractOperationValueTest {
    private static final Integer FIRST_VALUE = 35;
    private static final Integer SECOND_VALUE = 12;
    private static ValidationSubtractOperation validationSubtractOperation;

    @BeforeClass
    public static void beforeClass() {
        validationSubtractOperation = new ValidationSubtractOperationValue();
    }

    @Test
    public void validateDeleteOperation_Ok() {
        boolean expected = true;
        boolean actual =
                validationSubtractOperation.validateDeleteOperation(FIRST_VALUE, SECOND_VALUE);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void validateDeleteOperation_NotOk() {
        validationSubtractOperation.validateDeleteOperation(SECOND_VALUE, FIRST_VALUE);
    }
}
