package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationImplTest {
    private OperationHandler operationHandler;
    private Fruit apple;

    @Before
    public void setUp() {
        operationHandler = new BalanceOperationImpl();
        apple = new Fruit("apple");
    }

    @Test
    public void applyBalance_OK() {
        operationHandler.apply(new Transaction("b", apple, 18));
        Integer actual = Storage.storage.get(apple);
        Integer expected = 18;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
