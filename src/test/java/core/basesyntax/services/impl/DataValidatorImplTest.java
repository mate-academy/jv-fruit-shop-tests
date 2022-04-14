package core.basesyntax.services.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.IncorrectQuantityException;
import core.basesyntax.exception.InsufficientQuantityException;
import core.basesyntax.services.interfaces.DataValidator;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidatorImplTest {
    private static DataValidator dataValidator;

    @BeforeClass
    public static void beforeClass() {
        dataValidator = new DataValidatorImpl();
    }

    @Test
    public void test_checkIfQuantityPositiveWithPositiveArgument_Ok() {
        dataValidator.checkIfQuantityPositive(3);
    }

    @Test
    public void test_checkIfQuantityPositiveWithZeroArgument_Ok() {
        dataValidator.checkIfQuantityPositive(0);
    }

    @Test(expected = IncorrectQuantityException.class)
    public void test_checkIfQuantityPositiveWithNegativeArgument_NotOk() {
        dataValidator.checkIfQuantityPositive(-3);
    }

    @Test
    public void test_checkIfQuantitySufficientlyWhenBalanceIsHireThenNecessary_Ok() {
        int balance = 10;
        int necessary = 7;
        dataValidator.checkIfQuantitySufficiently(balance, necessary);
    }

    @Test
    public void test_checkIfQuantitySufficientlyWhenBalanceIsEqualsNecessary_Ok() {
        int balance = 8;
        int necessary = 8;
        dataValidator.checkIfQuantitySufficiently(balance, necessary);
    }

    @Test(expected = InsufficientQuantityException.class)
    public void test_checkIfQuantitySufficientlyWhenBalanceIsLessThenNecessary_NotOk() {
        int balance = 3;
        int necessary = 8;
        dataValidator.checkIfQuantitySufficiently(balance, necessary);
    }

    @Test
    public void test_checkIfDtoLine_Ok() {
        boolean actual = dataValidator.checkDtoLine("r,pineapple,30");
        assertTrue(actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_checkIfDtoLineWithNotCorrectOperation_NotOk() {
        dataValidator.checkDtoLine("rb,pineapple,30");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_checkIfDtoLineWithNegativeQuantity_NotOk() {
        dataValidator.checkDtoLine("rb,pineapple,-30");
    }
}
