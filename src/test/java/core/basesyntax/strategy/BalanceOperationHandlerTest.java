package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private final OperationHandler balanceHandler = new BalanceOperationHandler();

    @Test
    public void test_importingNewFruitsToStorage_ok() {
        Fruit apple = new Fruit();
        apple.setName("apple");
        Storage.storage.put(apple, 10);
        int expected = 100;
        int actual = balanceHandler.apply(new Transaction(Operation.B, apple, 100));
        assertEquals(expected, actual);
    }

    @Before
    public void start() {
        Storage.storage.clear();
    }
}
