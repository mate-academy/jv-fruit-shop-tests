package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit fruit;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        operationHandler = new SupplyOperationHandler();
        fruit = new Fruit("apple");
        fruitTransaction = new FruitTransaction("s", fruit, 8);
    }

    @Test
    public void supply_byCorrectAmount_ok() {
        operationHandler.process(fruitTransaction);
        int expected = 8;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supply_updateAmount_ok() {
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
