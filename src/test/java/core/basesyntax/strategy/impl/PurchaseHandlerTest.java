package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static OperationHandler operationHandler;
    private static final String INVALID_FRUIT = "lemon";
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_AMOUNT = 33;
    private static final int INVALID_BALANCE_IN_STORAGE = 10;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseHandler();
        Storage.getFruits().clear();
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }

    @Test(expected = RuntimeException.class)
    public void operateFruits_withNullData_notOk() {
        Storage.getFruits().clear();
        FruitTransaction fruitTransaction = new FruitTransaction(null, null, 0);
        operationHandler.operateFruits(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void operateFruits_withInvalidFruit_notOk() {
        Storage.getFruits().clear();
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, INVALID_FRUIT, VALID_AMOUNT);
        operationHandler.operateFruits(fruitTransaction);
    }

    @Test
    public void operateFruits_withValidData_Ok() {
        Storage.getFruits().clear();
        Storage.put(VALID_FRUIT, VALID_AMOUNT);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, VALID_FRUIT, VALID_AMOUNT);
        operationHandler.operateFruits(fruitTransaction);
        int expected = VALID_AMOUNT - VALID_AMOUNT;
        int actual = Storage.get(VALID_FRUIT);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void operateFruits_withInvalidAmount_notOk() {
        Storage.getFruits().clear();
        Storage.put(VALID_FRUIT, INVALID_BALANCE_IN_STORAGE);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, VALID_FRUIT, VALID_AMOUNT);
        operationHandler.operateFruits(fruitTransaction);
    }
}
