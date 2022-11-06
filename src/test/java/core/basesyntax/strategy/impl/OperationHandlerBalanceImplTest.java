package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerBalanceImplTest {
    private Storage storage;
    private Fruit apple;
    private Fruit kiwi;
    private OperationHandler operationHandlerBalance;

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        kiwi = new Fruit("kiwi");
        storage = new StorageImpl();
        operationHandlerBalance = new OperationHandlerBalanceImpl();

    }

    @Test
    public void applyMethodTest_Ok() {
        int expectedAmount = 15;
        operationHandlerBalance.apply(apple, 10);
        operationHandlerBalance.apply(kiwi, 15);
        int actualAmount = storage.getStorage().get(kiwi);
        assertEquals(expectedAmount, actualAmount);
    }
}
