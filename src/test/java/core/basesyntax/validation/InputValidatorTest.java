package core.basesyntax.validation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class InputValidatorTest {
    private static final int CORRECT_SIZE = 3;
    private static InputValidator inputValidator;

    @BeforeClass
    public static void beforeAll() {
        inputValidator = new InputValidatorImpl();
    }

    @Test
    public void isValidInput_correctData_Ok() {
        String[] testData = new String[]{"b", "apple", "100"};
        Assert.assertTrue("Input data is incorrect!", inputValidator.isValidInput(testData));
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_incorrectArraySize_NotOk() {
        String[] testData = new String[]{"b", "apple"};
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_incorrectOperation_NotOk() {
        String[] testData = new String[]{"111111", "apple", "100"};
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_emptyOperation_NotOk() {
        String[] testData = new String[]{"", "apple", "100"};
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_incorrectFruit_NotOk() {
        String[] testData = new String[]{"b", "12314", "100"};
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_emptyFruit_NotOk() {
        String[] testData = new String[]{"b", "", "100"};
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_incorrectAmount_NotOk() {
        String[] testData = new String[]{"111111", "apple", "amount"};
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_negativeAmount_NotOk() {
        String[] testData = new String[]{"b", "apple", "-10"};
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_emptyArray_NotOk() {
        String[] testData = new String[CORRECT_SIZE];
        inputValidator.isValidInput(testData);
    }
}
