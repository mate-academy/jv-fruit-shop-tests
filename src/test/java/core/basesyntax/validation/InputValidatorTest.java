package core.basesyntax.validation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class InputValidatorTest {
    private static final int CORRECT_SIZE = 3;
    private static final int INCORRECT_SIZE = 2;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static InputValidator inputValidator;

    @BeforeClass
    public static void beforeAll() {
        inputValidator = new InputValidatorImpl();
    }

    @Test
    public void isValidInput_correctData_Ok() {
        String[] testData = new String[CORRECT_SIZE];
        testData[OPERATION_INDEX] = "b";
        testData[FRUIT_INDEX] = "apple";
        testData[AMOUNT_INDEX] = "100";
        inputValidator.isValidInput(testData);
        Assert.assertTrue("Input data is incorrect!", true);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_incorrectArraySize_NotOk() {
        String[] testData = new String[INCORRECT_SIZE];
        testData[OPERATION_INDEX] = "b";
        testData[FRUIT_INDEX] = "apple";
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_incorrectOperation_NotOk() {
        String[] testData = new String[CORRECT_SIZE];
        testData[OPERATION_INDEX] = "111111";
        testData[FRUIT_INDEX] = "apple";
        testData[AMOUNT_INDEX] = "100";
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_emptyOperation_NotOk() {
        String[] testData = new String[CORRECT_SIZE];
        testData[OPERATION_INDEX] = "";
        testData[FRUIT_INDEX] = "apple";
        testData[AMOUNT_INDEX] = "100";
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_incorrectFruit_NotOk() {
        String[] testData = new String[CORRECT_SIZE];
        testData[OPERATION_INDEX] = "b";
        testData[FRUIT_INDEX] = "12314";
        testData[AMOUNT_INDEX] = "100";
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_emptyFruit_NotOk() {
        String[] testData = new String[CORRECT_SIZE];
        testData[OPERATION_INDEX] = "b";
        testData[FRUIT_INDEX] = "";
        testData[AMOUNT_INDEX] = "100";
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_incorrectAmount_NotOk() {
        String[] testData = new String[CORRECT_SIZE];
        testData[OPERATION_INDEX] = "111111";
        testData[FRUIT_INDEX] = "apple";
        testData[AMOUNT_INDEX] = "amount";
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_negativeAmount_NotOk() {
        String[] testData = new String[CORRECT_SIZE];
        testData[OPERATION_INDEX] = "b";
        testData[FRUIT_INDEX] = "apple";
        testData[AMOUNT_INDEX] = "-10";
        inputValidator.isValidInput(testData);
    }

    @Test (expected = RuntimeException.class)
    public void isValidInput_emptyArrayIndexes_NotOk() {
        String[] testData = new String[CORRECT_SIZE];
        inputValidator.isValidInput(testData);
    }
}
