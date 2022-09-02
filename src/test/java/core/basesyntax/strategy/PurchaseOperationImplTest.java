package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PurchaseOperationImplTest {
    private OperationHandler operationHandler;
    private Fruit apple;

    @Before
    public void beforeClass() throws Exception {
        operationHandler = new PurchaseOperationImpl();
        apple = new Fruit("apple");
    }

    @Test
    public void applyPurchase_OK() {
        Storage.storage.put(apple, 27);
        operationHandler.apply(new Transaction("p", apple, 5));
        Integer expected = 22;
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }
}