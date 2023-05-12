package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationStrategyTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new BalanceOperationStrategy();
    }

    @Test
    public void handle_ValidData_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 33);
        operationHandler.handle(fruitTransaction);
        assertEquals(33, (int) Storage.getFruitStorage().get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void handle_NullOperation_NotOk() {
        fruitTransaction.setOperation(null);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NullFruit_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 33);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NegativeQuantity_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, null, -10);
        operationHandler.handle(fruitTransaction);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruitStorage().clear();
    }
}
