package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler addHandler;
    private final Fruit apple = new Fruit("apple");
    private final Fruit orange = new Fruit("orange");

    @BeforeClass
    public static void beforeClass() {
        addHandler = new AddOperationHandler();
    }

    @Before
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void supply_ValidDataToStorage_OK() {
        Storage.storage.put(apple, 10);
        Storage.storage.put(orange, 20);
        int expected = 110;
        int actual = addHandler.apply(new Transaction(Operation.S, apple, 100));
        assertEquals(expected, actual);
    }

    @Test
    public void return_ValidDataToStorage_OK() {
        Storage.storage.put(apple, 10);
        Storage.storage.put(orange, 20);
        int expected = 110;
        int actual = addHandler.apply(new Transaction(Operation.R, apple, 100));
        assertEquals(expected, actual);
    }

    @Test
    public void add_InvalidDataToStorage_OK() {
        Storage.storage.put(apple, 10);
        Storage.storage.put(orange, 20);
        int expected = 0;
        int actual = addHandler.apply(new Transaction(null, null, 0));
        assertEquals(expected, actual);
    }
}
