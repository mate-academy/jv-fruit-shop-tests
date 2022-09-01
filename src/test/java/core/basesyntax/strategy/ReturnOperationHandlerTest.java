package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler = new ReturnOperationHandler();
    private Fruit fruit;

    @Before
    public void setUp() throws Exception {
        fruit = new Fruit("apple");
    }

    @Test
    public void applyReturn_OK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        returnOperationHandler.apply(new Transaction("p", new Fruit("apple"), 13));
        Integer expected = 27;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void negativeReturnValue_NotOK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        returnOperationHandler.apply(new Transaction("r", new Fruit("apple"), -13));
        Integer expected = Integer.valueOf(27);
        Integer actual = Storage.storage.get(fruit);
        assertFalse(expected.equals(actual));
    }

    @Test (expected = NullPointerException.class)
    public void nullReturnValue_NotOK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        returnOperationHandler.apply(new Transaction("r", new Fruit("apple"), null));
    }
}
