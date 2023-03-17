package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static OperationHandler operationHandler;
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_AMOUNT = 28;
    private static final int VALID_AMOUNT_SECOND = 22;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ReturnHandler();
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
