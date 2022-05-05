package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler;
    private Fruit fruit;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new PurchaseOperationHandler();
        fruit = new Fruit("apple");
        fruitTransaction = new FruitTransaction("p", fruit, 10);
    }

    @Test
    public void purchase_updateAmount_ok() {
        Storage.data.put(fruit, 15);
        operationHandler.process(fruitTransaction);
        int expected = 5;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_updateAmount_NotOk() {
        Storage.data.put(fruit, 5);
        operationHandler.process(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.data.clear();
    }
}
