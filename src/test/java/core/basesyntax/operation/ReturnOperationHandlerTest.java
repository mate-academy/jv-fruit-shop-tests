package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void apply_validFruitTransaction_Ok() {
        Storage.storage.put(new Fruit("banana"), 5);
        FruitTransaction fruitTransaction = new FruitTransaction("p", new Fruit("banana"), 10);
        returnOperationHandler.apply(fruitTransaction);
        Integer expected = 15;
        Integer actual = Storage.storage.get(new Fruit("banana"));
        assertEquals("", expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_NullFruitTransaction_NotOk() {
        returnOperationHandler.apply(null);
    }

    @Test
    public void apply_FruitTransactionIsPresentInStorage_Ok() {
        FruitTransaction bananaTransaction = new FruitTransaction("p", new Fruit("banana"), 10);
        Storage.storage.put(new Fruit("banana"), 5);
        Integer expected = 15;
        returnOperationHandler.apply(bananaTransaction);
        Integer actual = Storage.storage.get(new Fruit("banana"));
        assertEquals("", expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

}
