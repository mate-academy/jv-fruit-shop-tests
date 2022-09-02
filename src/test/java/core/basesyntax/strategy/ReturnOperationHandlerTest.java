package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private OperationHandler operationHandler;
    private Fruit apple;

    @Before
    public void setUp() throws Exception {
        operationHandler = new ReturnOperationHandler();
        apple = new Fruit("apple");
    }

    @Test
    public void applyReturn_OK() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("p", apple, 13));
        Integer expected = 27;
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void negativeReturnValue_NotOK() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("r", apple, -13));
        Integer expected = Integer.valueOf(27);
        Integer actual = Storage.storage.get(apple);
        assertFalse(expected.equals(actual));
    }

    @Test (expected = NullPointerException.class)
    public void nullReturnValue_NotOK() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("r", apple, null));
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
