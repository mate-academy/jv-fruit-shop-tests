package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private final OperationHandler balanceHandler = new BalanceOperationHandler();
    private final Fruit apple = new Fruit();

    @Test
    public void test_ImportingNewFruitsToStorage_OK() {
        Storage.storage.clear();
        apple.setName("apple");
        Storage.storage.put(apple, 10);
        int expected = 100;
        int actual = balanceHandler.apply(new Transaction(Operation.B, apple, 100));
        assertEquals(expected, actual);
        actual = balanceHandler.apply(new Transaction(Operation.R, apple, 10));
        assertNotEquals(expected, actual);
    }
}
