package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Test;

public class AddOperationHandlerTest {
    private final OperationHandler addHandler = new AddOperationHandler();
    private final Fruit apple = new Fruit("apple");
    private final Fruit orange = new Fruit("orange");

    private static void makeStorageEmpty() {
        Storage.storage.clear();
    }

    @Test
    public void test_AddingFruitsToStorage_OK() {
        makeStorageEmpty();
        Storage.storage.put(apple, 10);
        Storage.storage.put(orange, 20);
        int expected = 110;
        int actual = addHandler.apply(new Transaction(Operation.P, apple, 100));
        assertEquals(expected, actual);
    }

    @Test
    public void test_AddingWrongData_OK() {
        makeStorageEmpty();
        Storage.storage.put(apple, 10);
        Storage.storage.put(orange, 20);
        int expected = 0;
        int actual = addHandler.apply(new Transaction(null, null, 0));
        assertEquals(expected, actual);
    }
}
