package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.DataStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerOperationTest {
    private static final String FRUIT = "banana";
    private static final int FIRST_FRUIT_BALANCE = 80;
    private static final int SECOND_FRUIT_BALANCE = 25;
    private static final FruitTransaction.Operation OPERATION = PURCHASE;
    private FruitTransaction fruitTransaction;
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        operationHandler = new PurchaseHandlerOperation();
        DataStorage.fruitStorageMap.put(FRUIT, FIRST_FRUIT_BALANCE);
    }

    @Test
    public void handle_Ok() {
        fruitTransaction.setOperation(OPERATION);
        fruitTransaction.setFruit(FRUIT);
        fruitTransaction.setQuantity(SECOND_FRUIT_BALANCE);
        operationHandler.handle(fruitTransaction);
        int expected = FIRST_FRUIT_BALANCE - SECOND_FRUIT_BALANCE;
        int actual = DataStorage.fruitStorageMap.get(FRUIT);
        assertEquals(expected, actual);
    }

    @After
    public void clearAfterTest() {
        DataStorage.fruitStorageMap.clear();
    }
}
