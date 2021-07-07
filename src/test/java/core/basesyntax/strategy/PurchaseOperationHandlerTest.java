package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;
    private final Fruit apple = new Fruit("apple");
    private final Fruit orange = new Fruit("orange");

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new PurchaseOperationHandler();
    }

    @Before
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void test_SubtractingFruitsFromStorage_OK() {
        Storage.storage.put(apple, 10);
        Storage.storage.put(orange, 20);
        int actual = purchaseHandler.apply(new Transaction(Operation.P, apple, 5));
        int expected = 5;
        assertEquals(expected, actual);
    }
}
