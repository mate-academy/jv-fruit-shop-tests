package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.DataStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerOperationTest {
    private static final FruitTransaction.Operation OPERATION = BALANCE;
    private static final String FRUIT = "apple";
    private static final int FRUIT_QUANTITY = 50;
    private static final int WRONG_QUANTITY = -115;
    private FruitTransaction fruitTransaction;
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(OPERATION);
        fruitTransaction.setFruit(FRUIT);
        fruitTransaction.setQuantity(FRUIT_QUANTITY);
        operationHandler = new BalanceHandlerOperation();
    }

    @Test
    public void handle_Ok() {
        operationHandler.handle(fruitTransaction);
        int actual = DataStorage.fruitStorageMap.get(FRUIT);
        assertEquals(FRUIT_QUANTITY, actual);
    }

    @Test
    public void handle_balanceSetNegativeQuantity_NotOk() {
        fruitTransaction.setQuantity(-10);
        try {
            operationHandler.handle(fruitTransaction);
        } catch (RuntimeException e) {
            throw new RuntimeException("wrong quantity for balance operation: "
                    + WRONG_QUANTITY, e);
        }
    }

    @After
    public void clearAfterTest() {
        DataStorage.fruitStorageMap.clear();
    }
}
