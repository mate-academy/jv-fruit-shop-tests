package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private final OperationHandler balanceHandler = new BalanceOperationHandler();
    private final Fruit apple = new Fruit("apple");
    private final Fruit orange = new Fruit("orange");

    private static void makeStorageEmpty() {
        Storage.storage.clear();
    }

    @Test
    public void test_ImportingNewFruitsToStorage_OK() {
        makeStorageEmpty();
        Storage.storage.put(apple, 10);
        Storage.storage.put(orange, 20);
        int expected = 100;
        int actual = balanceHandler.apply(new Transaction(Operation.B, apple, 100));
        assertEquals(expected, actual);
    }
}
