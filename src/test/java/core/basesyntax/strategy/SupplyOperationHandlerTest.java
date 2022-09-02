package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationHandler operationHandler;
    private Fruit apple;

    @Before
    public void setUp() throws Exception {
        operationHandler = new SupplyOperationHandler();
        apple = new Fruit("apple");
    }

    @Test
    public void applySupply_OK() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("s", apple, 13));
        Integer expected = 27;
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void negativeSupplyValue_NotOK() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("s", apple, -13));
        Integer expected = Integer.valueOf(27);
        Integer actual = Storage.storage.get(apple);
        assertFalse(expected.equals(actual));
    }

    @Test (expected = NullPointerException.class)
    public void nullSupplyValue_NotOK() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("s", apple, null));
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
