package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalaceOperationHandlerTest {
    private static OperationHandler balaceOperationHandler;
    private FruitTransaction fruitTransaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        balaceOperationHandler = new BalaceOperationHandler();
    }

    @Test
    public void apply_FruitTransactionIsValid_Ok() {
        fruitTransaction = new FruitTransaction("b", new Fruit("banana"), 10);
        fruit = new Fruit("banana");
        balaceOperationHandler.apply(fruitTransaction);
        Integer expected = 10;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_FruitTransactionIsNull_NotOk() {
        balaceOperationHandler.apply(null);
    }

    @Test
    public void apply_FruitTransactionIsPresentInStorage_Ok() {
        fruitTransaction = new FruitTransaction("b", new Fruit("banana"), 10);
        fruit = new Fruit("banana");
        Storage.storage.put(fruit, 5);
        Integer expected = 15;
        balaceOperationHandler.apply(fruitTransaction);
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
