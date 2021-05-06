package core.basesyntax.validate;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ValidateAddOperationValueTest {
    private static final Integer GOOD_QUANTITY = 20;
    private static final Integer WRONG_QUANTITY = -1;
    private static ValidationAddOperation validationAddOperation;

    @BeforeClass
    public static void beforeClass() {
        validationAddOperation = new ValidateAddOperationValue();
    }

    @Test
    public void validateAddOperation_Ok() {
        boolean expected = true;
        boolean actual = validationAddOperation.validateAddOperation(GOOD_QUANTITY);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void validateAddOperation_NotOk() {
        validationAddOperation.validateAddOperation(WRONG_QUANTITY);
    }
}
