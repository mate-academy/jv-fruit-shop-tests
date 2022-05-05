package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit fruit;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        operationHandler = new ReturnOperationHandler();
        fruit = new Fruit("apple");
        fruitTransaction = new FruitTransaction("r", fruit, 8);
    }

    @Test
    public void return_byCorrectAmount_ok() {
        operationHandler.process(fruitTransaction);
        int expected = 8;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void return_updateAmount_ok() {
        Storage.data.put(fruit, 2);
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
