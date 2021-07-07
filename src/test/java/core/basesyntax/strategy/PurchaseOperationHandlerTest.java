package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler purchaseHandler;

    @Test
    public void test_subtractingFruitsFromStorage_ok() {
        purchaseHandler = new PurchaseOperationHandler();
        Fruit apple = new Fruit();
        apple.setName("apple");
        Storage.storage.put(apple, 10);
        int actual = purchaseHandler.apply(new Transaction(Operation.P, apple, 5));
        int expected = 5;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void test_notEnoughFruitsSubtracting_ok() {
        purchaseHandler = new PurchaseOperationHandler();
        Fruit banana = new Fruit();
        banana.setName("banana");
        Storage.storage.put(banana, 10);
        purchaseHandler.apply(new Transaction(Operation.P, banana, 11));

    }

    @Before
    public void start() {
        Storage.storage.clear();
    }
}
