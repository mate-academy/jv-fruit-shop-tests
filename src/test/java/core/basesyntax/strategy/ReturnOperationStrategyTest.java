package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationStrategyTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new ReturnOperationStrategy();
    }

    @Test
    public void handle_ValidData_Ok() {
        Storage.getFruitStorage().put("orange", 1);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 21);
        operationHandler.handle(fruitTransaction);
        assertEquals(22, (int) Storage.getFruitStorage().get("orange"));
    }

    @Test(expected = RuntimeException.class)
    public void handle_WrongOperation_NotOk() {
        Storage.getFruitStorage().clear();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 21);
        operationHandler.handle(fruitTransaction);
        assertEquals(22, (int) Storage.getFruitStorage().get("orange"));
    }

    @Test(expected = RuntimeException.class)
    public void handle_NullOperation_NotOk() {
        fruitTransaction = new FruitTransaction(null, "orange", 33);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NullFruit_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, null, 33);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NegativeQuantity_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", -10);
        operationHandler.handle(fruitTransaction);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruitStorage().clear();
    }
}
