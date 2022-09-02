package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit apple;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new SupplyOperationHandler();
        apple = new Fruit("apple");
    }

    @Test
    public void apply_Ok() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("s", apple, 13));
        Integer expected = 27;
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_negativeValue_Ok() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("s", apple, -13));
        Integer expected = Integer.valueOf(1);
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void apply_nullValue_notOk() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("s", apple, null));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
