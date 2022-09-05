package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit apple;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
        apple = new Fruit("apple");
    }

    @Test
    public void apply_ok() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("p", apple, 9));
        Integer expected = 5;
        Integer actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_negativeValue_notOk() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("p", apple, -9));
        Integer expected = Integer.valueOf(5);
        Integer actual = Storage.storage.get(this.apple);
        assertFalse(expected.equals(actual));
    }

    @Test (expected = NullPointerException.class)
    public void apply_nullValue_notOk() {
        Storage.storage.put(apple, 14);
        operationHandler.apply(new Transaction("p", apple, null));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
