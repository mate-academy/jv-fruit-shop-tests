package core.basesyntax.validate;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorOfDataTest {
    private static final String VALID_OPERATION = "b";
    private static final String INVALID_OPERATION = "m";
    private static final String VALID_FRUIT_NAME = "banana";
    private static final String INVALID_FRUIT_NAME = "7dv";
    private static final String VALID_QUANTITY = "30";
    private static final String INVALID_QUANTITY = "-30";

    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorOfData();
    }

    @Test
    public void validationData_Ok() {
        boolean expected = true;
        boolean actual =
                validator.validationData(VALID_OPERATION,
                        VALID_FRUIT_NAME,
                        VALID_QUANTITY);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void validationData_NotOk() {
        validator.validationData(INVALID_OPERATION, INVALID_FRUIT_NAME, INVALID_QUANTITY);
    }

    @Test
    public void validationOfOperation_Ok() {
        boolean expected = true;
        boolean actual = validator.validationOfOperation(VALID_OPERATION);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void validationOfOperation_NotOk() {
        validator.validationOfOperation(INVALID_OPERATION);
    }

    @Test
    public void validationOfFruitName_Ok() {
        boolean expected = true;
        boolean actual = validator.validationOfFruitName(VALID_FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void validationOfFruitName_NotOk() {
        validator.validationOfFruitName(INVALID_FRUIT_NAME);
    }

    @Test
    public void validationOfQuantity_Ok() {
        boolean expected = true;
        boolean actual = validator.validationOfQuantity(VALID_QUANTITY);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void validationOfQuantity_NotOk() {
        validator.validationOfQuantity(INVALID_QUANTITY);
    }
}
