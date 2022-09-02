package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler operationHandler;
    private Fruit apple;

    @Before
    public void setUp() throws Exception {
        operationHandler = new BalanceOperationHandler();
        apple = new Fruit("apple");
    }

    @Test
    public void applyBalance_OK() {
        operationHandler.apply(new Transaction("b", apple, 14));
        Integer actual = Storage.storage.get(apple);
        Integer expected = 14;
        assertEquals(expected, actual);
    }

    @Test
    public void nullValue_ApplyBalance_OK() {
        Storage.storage.put(apple, null);
        Integer expected = null;
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
