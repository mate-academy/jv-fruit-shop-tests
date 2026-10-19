package core.basesyntax.operation.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationTest {
    private static final String FRUIT = "apple";
    private PurchaseOperation purchaseOperation;

    @Before
    public void setUp() {
        Storage.storage.clear();
        Storage.storage.put(FRUIT, 50);
        purchaseOperation = new PurchaseOperation();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void handle_purchase_shouldSubtractQuantity() {
        purchaseOperation.handle(FRUIT, 20);

        int expected = 30;
        int actual = Storage.storage.get(FRUIT);
        assertEquals(expected, actual);
    }
}
