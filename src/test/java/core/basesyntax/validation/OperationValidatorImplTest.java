package core.basesyntax.validation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationValidatorImplTest {
    private static OperationValidator operationValidator;
    private String[] testString;

    @BeforeClass
    public static void beforeAll() {
        operationValidator = new OperationValidatorImpl();
    }

    @Test(expected = RuntimeException.class)
    public void isOperationValid_nullOperation_notOk() {
        testString = new String[]{null,"banana","100"};
        operationValidator.isOperationValid(testString);
    }

    @Test(expected = RuntimeException.class)
    public void isOperationValid_nullFruitName_notOk() {
        testString = new String[]{"p",null,"100"};
        operationValidator.isOperationValid(testString);
    }

    @Test(expected = RuntimeException.class)
    public void isOperationValid_nullQuantity_notOk() {
        testString = new String[]{"p","banana",null};
        operationValidator.isOperationValid(testString);
    }

    @Test(expected = RuntimeException.class)
    public void isOperationValid_wrongLengthOfArrayBigger_notOk() {
        testString = new String[]{"p","banana","100","50"};
        operationValidator.isOperationValid(testString);
    }

    @Test(expected = RuntimeException.class)
    public void isOperationValid_wrongLengthOfArraySmaller_notOk() {
        testString = new String[]{"p","banana"};
        operationValidator.isOperationValid(testString);
    }

    @Test(expected = RuntimeException.class)
    public void isOperationValid_incorrectQuantityWritting_notOk() {
        testString = new String[]{"p","banana","100*"};
        operationValidator.isOperationValid(testString);
    }

    @Test(expected = RuntimeException.class)
    public void isOperationValid_negativeQuantity_notOk() {
        testString = new String[]{"p","banana","-1"};
        operationValidator.isOperationValid(testString);
    }

    @Test(expected = RuntimeException.class)
    public void isOperationValid_fruitNaming_notOk() {
        testString = new String[]{"p","bana na","1"};
        operationValidator.isOperationValid(testString);
    }

    @Test(expected = RuntimeException.class)
    public void isOperationValid_operationTypeWrongLetter_notOk() {
        testString = new String[]{"a","banana","1"};
        operationValidator.isOperationValid(testString);
    }

    @Test(expected = RuntimeException.class)
    public void isOperationValid_operationTypeTwoLetters_notOk() {
        testString = new String[]{"ps","banana","1"};
        operationValidator.isOperationValid(testString);
    }

    @Test
    public void isOperationValid_correctStringArrayAndPurchaseOperation_ok() {
        testString = new String[]{"p","banana","100"};
        boolean expected = operationValidator.isOperationValid(testString);
        Assert.assertTrue(expected);
    }

    @Test
    public void isOperationValid_supplyOperation_ok() {
        testString = new String[]{"s","banana","100"};
        boolean expected = operationValidator.isOperationValid(testString);
        Assert.assertTrue(expected);
    }

    @Test
    public void isOperationValid_returnOperation_ok() {
        testString = new String[]{"r","banana","100"};
        boolean expected = operationValidator.isOperationValid(testString);
        Assert.assertTrue(expected);
    }

    @Test
    public void isOperationValid_balanceOperation_ok() {
        testString = new String[]{"b","banana","100"};
        boolean expected = operationValidator.isOperationValid(testString);
        Assert.assertTrue(expected);
    }
}
