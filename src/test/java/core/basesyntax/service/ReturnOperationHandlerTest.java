package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler handler;
    private static Operation operation;
    private static Fruit fruit;
    private FruitTransaction transaction;

    @BeforeClass
    public static void beforeClass() {
        operation = Operation.RETURN;
        fruit = new Fruit("banana");
        handler = new ReturnOperationHandler();
        Storage.storage.clear();
        Storage.storage.put(fruit,9);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }

    @Test
    public void handle_ok() {
        transaction = new FruitTransaction(operation, fruit, 1);
        handler.handle(transaction);
        assertEquals(Integer.valueOf(10), Storage.storage.get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void handle_notOk() {
        transaction = new FruitTransaction(operation, fruit, -1);
        handler.handle(transaction);
    }
}
