package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler operationHandler;
    private static final int VALID_AMOUNT = 25;
    private static final int VALID_AMOUNT_SECOND = 15;
    private static final int INVALID_AMOUNT = -5;
    private static final String INVALID_FRUIT = null;
    private static final String VALID_FRUIT = "banana";

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceHandler();
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }

    @Test(expected = RuntimeException.class)
    public void operateFruits_withNullFruit_notOk() {
        operationHandler.operateFruits(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        INVALID_FRUIT, VALID_AMOUNT));
    }

    @Test(expected = RuntimeException.class)
    public void operateFruits_withInvalidAmount_notOk() {
        operationHandler.operateFruits(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        VALID_FRUIT, INVALID_AMOUNT));
    }

    @Test
    public void operateFruits_withValidData_Ok() {
        Storage.getFruits().clear();
        operationHandler.operateFruits(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        VALID_FRUIT, VALID_AMOUNT));
        int expected = VALID_AMOUNT;
        int actual = Storage.getFruits().get(VALID_FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    public void operateFruits_withSomeOperationsInStorage_Ok() {
        Storage.getFruits().clear();
        operationHandler.operateFruits(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        VALID_FRUIT, VALID_AMOUNT));
        operationHandler.operateFruits(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        VALID_FRUIT, VALID_AMOUNT_SECOND));
        int expected = VALID_AMOUNT + VALID_AMOUNT_SECOND;
        int actual = Storage.getFruits().get(VALID_FRUIT);
        assertEquals(expected, actual);
    }
}
