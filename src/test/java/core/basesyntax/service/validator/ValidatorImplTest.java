package core.basesyntax.service.validator;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;
    private static String[] correctData;
    private static String[] dataWithNumberBelowZero;
    private static String[] dataWithWrongOperationType;
    private static String[] dataWithWrongFruitName;
    private static String[] dataWithAlphabeticAmount;
    private static String[] arrayLengthNotThree;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
        correctData = new String[]{"b","banana","100"};
        dataWithNumberBelowZero = new String[]{"b","banana","-100"};
        dataWithAlphabeticAmount = new String[]{"b","banana","three"};
        dataWithWrongFruitName = new String[]{"b","banana13","100"};
        dataWithWrongOperationType = new String[]{"type","banana","100"};
        arrayLengthNotThree = new String[4];
    }

    @Test
    public void isValidData_arrayWithCorrectData_ok() {
        boolean expected = true;
        boolean actual = validator.isValidData(correctData);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValidData_arrayWithNumberBelowZero_notOk() {
        validator.isValidData(dataWithNumberBelowZero);
    }

    @Test(expected = RuntimeException.class)
    public void isValidData_arrayWithAlphabeticAmount_notOk() {
        validator.isValidData(dataWithAlphabeticAmount);
    }

    @Test(expected = RuntimeException.class)
    public void isValidData_arrayWithWrongFruitName_notOk() {
        validator.isValidData(dataWithWrongFruitName);
    }

    @Test(expected = RuntimeException.class)
    public void isValidData_arrayWithWrongOperation_notOk() {
        validator.isValidData(dataWithWrongOperationType);
    }

    @Test(expected = RuntimeException.class)
    public void isValidData_arraysLengthNotTree_notOk() {
        validator.isValidData(arrayLengthNotThree);
    }
}
