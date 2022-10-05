package core.basesyntax.service.base;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;

public abstract class OperationHandlerTestBase {
    private Map<Operation, ? extends OperationHandler> instances;

    protected abstract Map<Operation, ? extends OperationHandler> getInstances();

    private Operation operation;
    private Fruit fruit;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        instances = getInstances();
        fruit = new Fruit("banana");
    }

    @Test
    public void handle_Balance_Operation_ok() {
        operation = Operation.BALANCE;
        fruitTransaction = new FruitTransaction(operation, fruit, 10);
        instances.get(operation).handle(fruitTransaction);
        assertEquals(Integer.valueOf(10), Storage.storage.get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void handle_Balance_Operation_notOk() {
        operation = Operation.BALANCE;
        fruitTransaction = new FruitTransaction(operation, fruit, -10);
        instances.get(operation).handle(fruitTransaction);
    }

    @Test
    public void handle_Purchase_Operation_ok() {
        operation = Operation.PURCHASE;
        fruitTransaction = new FruitTransaction(operation, fruit, 1);
        instances.get(operation).handle(fruitTransaction);
        assertEquals(Integer.valueOf(9), Storage.storage.get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void handle_Purchase_Operation_notOk() {
        operation = Operation.PURCHASE;
        fruitTransaction = new FruitTransaction(operation, fruit, -10);
        instances.get(operation).handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_Purchase_Operation_Not_Enough_Quantity_notOk() {
        operation = Operation.PURCHASE;
        fruitTransaction = new FruitTransaction(operation, fruit, 100);
        instances.get(operation).handle(fruitTransaction);
    }

    @Test
    public void handle_Return_Operation_ok() {
        operation = Operation.RETURN;
        fruitTransaction = new FruitTransaction(operation, fruit, 1);
        instances.get(operation).handle(fruitTransaction);
        assertEquals(Integer.valueOf(10), Storage.storage.get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void handle_Return_Operation_notOk() {
        operation = Operation.RETURN;
        fruitTransaction = new FruitTransaction(operation, fruit, -1);
        instances.get(operation).handle(fruitTransaction);
    }

    @Test
    public void handle_Supply_Operation_ok() {
        operation = Operation.SUPPLY;
        fruitTransaction = new FruitTransaction(operation, fruit, 1);
        instances.get(operation).handle(fruitTransaction);
        assertEquals(Integer.valueOf(11), Storage.storage.get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void handle_Supply_Operation_notOk() {
        operation = Operation.SUPPLY;
        fruitTransaction = new FruitTransaction(operation, fruit, -1);
        instances.get(operation).handle(fruitTransaction);
    }
}
