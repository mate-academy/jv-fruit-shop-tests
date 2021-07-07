package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.Test;

public class AddOperationHandlerTest {
    private final OperationHandler addHandler = new AddOperationHandler();
    private final Fruit apple = new Fruit();

    @Test
    public void test_AddingFruitsToStorage_OK() {
        Storage.storage.put(apple, 10);
        int expected = 20;
        int actual = addHandler.apply(new Transaction(Operation.S, apple, 10));
        assertEquals(expected, actual);
    }

    @Test
    public void test_AddingWrongData_OK() {
        Storage.storage.put(apple, 10);
        int expected = 0;
        int actual = addHandler.apply(new Transaction(Operation.S, apple, 0));
        assertNotEquals(expected, actual);
    }

    @Before
    public void start() {
        Storage.storage.clear();
        apple.setName("apple");
    }
}
