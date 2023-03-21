package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationStrategyTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new PurchaseOperationStrategy();
    }

    @Test
    public void handle_ValidData_Ok() {
        Storage.getFruitStorage().put("apple", 33);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 33);
        operationHandler.handle(fruitTransaction);
        assertEquals(0, (int) Storage.getFruitStorage().get("apple"));
    }

    @Test(expected = RuntimeException.class)
    public void handle_NotValidFruit_NotOk() {
        Storage.getFruitStorage().clear();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 33);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NotValidQuantity_NotOk() {
        Storage.getFruitStorage().put("apple", 33);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 70);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NullOperation_NotOk() {
        fruitTransaction = new FruitTransaction(null, "apple", 33);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NullFruit_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, null, 33);
        operationHandler.handle(fruitTransaction);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruitStorage().clear();
    }
}
