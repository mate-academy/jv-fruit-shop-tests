package core.basesyntax.service;

import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {
    private static final int DEFAULT_CAPACITY = 3;
    private static final int AMOUNT = 2;
    private static final int FRUIT_TYPE = 1;
    private static final String SEPARATOR = ",";
    private static final int OPERATION_TYPE = 0;
    private static final String[] incorrectLengthArray = {"b"};
    private static final String[] incorrectAmountArray = {"b","banana","-2"};
    private static final String[] incorrectValuesArray = {"1","123","word"};
    private static Validator validator;

    @Before
    public void setUp() throws Exception {
        validator = new Validator();
    }

    @Test(expected = RuntimeException.class)
    public void validate_incorrectArrayLength_NotOk() {
        validator.validate(incorrectLengthArray);
    }

    @Test(expected = RuntimeException.class)
    public void validate_incorrectFruitAmount_NotOk() {
        validator.validate(incorrectAmountArray);
    }

    @Test(expected = RuntimeException.class)
    public void validate_incorrectIncomingData_NotOk() {
        validator.validate(incorrectValuesArray);
    }
}
