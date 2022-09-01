package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler();
    private Transaction expectedTransaction;
    private Fruit fruit;

    @Before
    public void setUp() throws Exception {
        expectedTransaction = new Transaction("b", new Fruit("apple"), 14);
        fruit = expectedTransaction.getFruit();
    }

    @Test
    public void applyBalance_OK() {
        balanceOperationHandler.apply(new Transaction("b", new Fruit("apple"), 14));
        Integer actual = Storage.storage.get(new Fruit("apple"));
        Integer expected = 14;
        assertEquals(expected, actual);
    }

    @Test
    public void nullValue_ApplyBalance_OK() {
        Storage.storage.put(fruit, null);
        Integer expected = null;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}