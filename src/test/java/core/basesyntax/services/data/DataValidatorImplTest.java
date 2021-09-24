package core.basesyntax.services.data;

import org.junit.Assert;
import org.junit.Test;

public class DataValidatorImplTest {
    public static final int NUMBER_OF_COLUMNS = 3;
    public static final int INCORRECT_NUMBER_OF_COLUMNS = 5;
    public static final int INDEX_OF_AMOUNT = 2;
    public static final String INCORRECT_INDEX_OF_AMOUNT = "0";
    private DataValidatorImpl validator = new DataValidatorImpl();

    @Test
    public void validate_incorrectNumberOfColumns_notOk() {
        String[] testData = new String[INCORRECT_NUMBER_OF_COLUMNS];
        Assert.assertThrows("Wrong data input",
                RuntimeException.class, () -> validator.validate(testData));
    }

    @Test
    public void validate_incorrectIndexOfAmount_notOk() {
        String[] testData = new String[NUMBER_OF_COLUMNS];
        testData[INDEX_OF_AMOUNT] = INCORRECT_INDEX_OF_AMOUNT;
        Assert.assertThrows("Wrong data input",
                RuntimeException.class, () -> validator.validate(testData));
    }

    @Test
    public void validate_correctIndexOfAmountAndNumberOfColumns_Ok() {
        String[] testData = new String[NUMBER_OF_COLUMNS];
        testData[INDEX_OF_AMOUNT] = String.valueOf(INDEX_OF_AMOUNT);
        Assert.assertTrue(validator.validate(testData));
    }
}
