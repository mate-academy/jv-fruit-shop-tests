package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationStrategyTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new SupplyOperationStrategy();
    }

    @Test
    public void handle_ValidData_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "kiwi", 22);
        operationHandler.handle(fruitTransaction);
        System.out.println(Storage.getFruitStorage().get("kiwi"));
        assertEquals(22, (int) Storage.getFruitStorage().get("kiwi"));
        operationHandler.handle(fruitTransaction);
        assertEquals(44, (int) Storage.getFruitStorage().get("kiwi"));
    }

    @Test(expected = RuntimeException.class)
    public void handle_NullOperation_NotOk() {
        fruitTransaction.setOperation(null);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NullFruit_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, null, 33);
        operationHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NegativeQuantity_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "kiwi", -10);
        operationHandler.handle(fruitTransaction);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruitStorage().clear();
    }
}
