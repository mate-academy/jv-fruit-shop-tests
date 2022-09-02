package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final FruitTransaction fruitTransaction
            = new FruitTransaction("p", new Fruit("banana"), 10);
    private static final Fruit fruit = new Fruit("banana");

    private static ReturnOperationHandler returnOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void apply_validFruitTransaction_Ok() {
        Storage.storage.put(fruit, 5);
        returnOperationHandler.apply(fruitTransaction);
        Integer expected = 15;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_NullFruitTransaction_NotOk() {
        returnOperationHandler.apply(null);
    }

    @Test
    public void apply_FruitTransactionIsPresentInStorage_Ok() {
        Storage.storage.put(fruit, 5);
        Integer expected = 15;
        returnOperationHandler.apply(fruitTransaction);
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

}
