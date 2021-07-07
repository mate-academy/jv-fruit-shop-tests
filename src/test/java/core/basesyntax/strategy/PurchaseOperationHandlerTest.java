package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private final OperationHandler purchaseHandler = new PurchaseOperationHandler();
    private final Fruit apple = new Fruit();
    private final Fruit banana = new Fruit();

    @Test
    public void test_SubtractingFruitsFromStorage() {
        apple.setName("apple");
        banana.setName("banana");
        Storage.storage.clear();
        Storage.storage.put(apple, 10);
        Storage.storage.put(banana, 20);
        int actual = purchaseHandler.apply(new Transaction(Operation.P, apple, 5));
        int expected = 5;
        assertEquals(expected, actual);
    }
}
