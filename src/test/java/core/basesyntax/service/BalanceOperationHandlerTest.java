package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler handler;
    private static Operation operation;
    private static Fruit fruit;
    private FruitTransaction transaction;

    @BeforeClass
    public static void beforeClass() {
        operation = Operation.BALANCE;
        fruit = new Fruit("banana");
        handler = new BalanceOperationHandler();
    }

    @Test
    public void handle_Balance_Operation_ok() {
        transaction = new FruitTransaction(operation, fruit, 10);
        handler.handle(transaction);
        assertEquals(Integer.valueOf(10), Storage.storage.get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void handle_Balance_Operation_notOk() {
        transaction = new FruitTransaction(operation, fruit, -10);
        handler.handle(transaction);
    }
}
