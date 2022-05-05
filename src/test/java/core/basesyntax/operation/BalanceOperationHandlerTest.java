package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler operationHandler;
    private Fruit fruit;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationHandler = new BalanceOperationHandler();
        fruit = new Fruit("apple");
        fruitTransaction = new FruitTransaction("b", fruit, 8);
    }

    @Test
    public void balance_byCorrectAmount_ok() {
        operationHandler.process(fruitTransaction);
        int expected = 8;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void balance_updateAmount_ok() {
        Storage.data.put(new Fruit("apple"), 2);
        operationHandler.process(fruitTransaction);
        int expected = 10;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.data.clear();
    }
}
