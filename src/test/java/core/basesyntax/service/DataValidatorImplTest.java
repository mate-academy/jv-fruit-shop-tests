package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidatorImplTest {
    private static DataValidator dataValidator;
    private String mockFileLine;
    private String[] expected;
    private String[] actual;

    @BeforeClass
    public static void setUp() {
        dataValidator = new DataValidatorImpl();
    }

    @Test
    public void checkFruitRecordString_Ok() {
        mockFileLine = "b,banana,10";
        expected = new String[]{"b", "banana", "10"};
        actual = dataValidator.validate(mockFileLine);
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test (expected = ValidationException.class)
    public void checkWrongOperation_NotOk() {
        mockFileLine = "a,banana,10";
        dataValidator.validate(mockFileLine);
    }

    @Test
    public void checkAnotherOperationAndFruit_Ok() {
        mockFileLine = "s,avocado,112";
        expected = new String[]{"s", "avocado", "112"};
        actual = dataValidator.validate(mockFileLine);
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test (expected = ValidationException.class)
    public void checkWrongFruitPattern_NotOk() {
        mockFileLine = "a,ban2ana,10";
        dataValidator.validate(mockFileLine);
    }

    @Test (expected = ValidationException.class)
    public void checkWrongQuantity_NotOk() {
        mockFileLine = "a,banana,-10";
        dataValidator.validate(mockFileLine);
    }

    @Test (expected = ValidationException.class)
    public void checkIncorrectFruitRecordLine_NotOk() {
        mockFileLine = "s,avocado10";
        dataValidator.validate(mockFileLine);
    }

    @Test (expected = ValidationException.class)
    public void checkEmptyFruitRecordLine_NotOk() {
        mockFileLine = "";
        dataValidator.validate(mockFileLine);
    }

    @Test (expected = ValidationException.class)
    public void checkNullFruitRecordLine_NotOk() {
        mockFileLine = null;
        dataValidator.validate(mockFileLine);
    }
}
