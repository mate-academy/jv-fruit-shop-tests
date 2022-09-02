package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalaceOperationHandlerTest {
    private static final FruitTransaction fruitTransaction
            = new FruitTransaction("b", new Fruit("banana"), 10);
    private static final Fruit fruit = new Fruit("banana");
    private BalaceOperationHandler balaceOperationHandler;

    @Before
    public void setUp() {
        balaceOperationHandler = new BalaceOperationHandler();
    }

    @Test
    public void apply_validFruitTransaction_Ok() {
        balaceOperationHandler.apply(fruitTransaction);
        Integer expected = 10;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_NullFruitTransaction_NotOk() {
        balaceOperationHandler.apply(null);
    }

    @Test
    public void apply_FruitTransactionIsPresentInStorage_Ok() {
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
