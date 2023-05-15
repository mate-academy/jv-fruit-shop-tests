package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final Operation TEST_OPERATION = Operation.BALANCE;
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_QUANTITY = 100;
    private static final int EXPECTED_TEST_SIZE = 1;
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new BalanceOperationHandler();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void handle_addToStorage_ok() {
        fruitTransaction.setOperation(TEST_OPERATION);
        fruitTransaction.setFruit(TEST_FRUIT);
        fruitTransaction.setQuantity(TEST_QUANTITY);
        operationHandler.handle(fruitTransaction);
        assertEquals(EXPECTED_TEST_SIZE, Storage.fruits.size());
        assertTrue(Storage.fruits.containsKey(fruitTransaction.getFruit()));
        assertTrue(Storage.fruits.containsValue(fruitTransaction.getQuantity()));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
