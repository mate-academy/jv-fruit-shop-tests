package core.basesyntax;

import core.basesyntax.service.validation.DataValidator;
import core.basesyntax.service.validation.DataValidatorImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataValidatorTest {
    private static final String[] DATA_ROW = new String[]{"b", "banana", "10"};
    private static final String[] INCORRECT_ROW_LENGTH = new String[]{"banana"};
    private static final String[] INCORRECT_FRUIT_AMOUNT = new String[]{"p", "banana", "-10"};
    private static final String[] EMPTY_FRUIT_NAME = new String[]{"p", "", "10"};
    private static final String[] EMPTY_OPERATION_TYPE = new String[]{"", "banana", "10"};
    private static DataValidator dataValidator;

    @Before
    public void setUp() {
        dataValidator = new DataValidatorImpl();
    }

    @Test
    public void validateRow_validate_OK() {
        Assert.assertTrue("Test failed! Expected result differs from actual result!",
                dataValidator.validate(DATA_ROW));
    }

    @Test(expected = RuntimeException.class)
    public void validateRowWithIncorrectLength_validate_Not_OK() {
        dataValidator.validate(INCORRECT_ROW_LENGTH);
    }

    @Test(expected = RuntimeException.class)
    public void validateIncorrectFruitAmount_validate_Not_OK() {
        dataValidator.validate(INCORRECT_FRUIT_AMOUNT);
    }

    @Test(expected = RuntimeException.class)
    public void validateIncorrectFruitName_validate_Not_OK() {
        dataValidator.validate(EMPTY_FRUIT_NAME);
    }

    @Test(expected = RuntimeException.class)
    public void validateIncorrectOperationType_validate_Not_OK() {
        dataValidator.validate(EMPTY_OPERATION_TYPE);
    }
}
