package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static Map<String, Integer> fruitStorage;
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new BalanceOperationHandler();
        fruitStorage = Storage.fruits;
    }

    @AfterClass
    public static void afterClass() throws Exception {
        fruitStorage.clear();
    }

    @Test
    public void balanceHandleOeration_validOperation_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20);
        operationHandler.handle(fruitTransaction);
        int actual = fruitStorage.get("apple");
        int expected = 20;
        assertEquals(expected, actual);
    }

    @Test
    public void balanceHandleOperation_zeroValue_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 0);
        operationHandler.handle(fruitTransaction);
        int actual = fruitStorage.get("apple");
        int expected = 0;
        assertEquals(expected, actual);
    }
}
