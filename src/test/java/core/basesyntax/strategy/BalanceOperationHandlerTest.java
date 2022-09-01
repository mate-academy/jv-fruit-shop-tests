package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BalanceOperationHandlerTest {
    private Transaction expectedTransaction;

    @Before
    public void setUp() throws Exception {
        expectedTransaction = new Transaction("b", new Fruit("apple"), 14);
    }

    @Test
    public void applyBalance_OK() {
        Fruit fruit = expectedTransaction.getFruit();
        Storage.storage.put(fruit, expectedTransaction.getQuantity());
        int expected = expectedTransaction.getQuantity();
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void nullValue_ApplyBalance_OK() {
        Fruit fruit = expectedTransaction.getFruit();
        Storage.storage.put(fruit, null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}