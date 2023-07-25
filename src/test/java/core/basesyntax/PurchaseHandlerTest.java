package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private OperationHandler purchaseHandler;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseHandler();
    }

    @Test
    public void purchase_NegativeBalance_ExceptionThrown() {
        Storage.storageMap.put("apple", 5);
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> purchaseHandler.doOperation("apple", 10));
        assertEquals(runtimeException.getMessage(), "Balance of fruit can't be negative");
    }

    @Test
    public void purchase_ValidData_Ok() {
        Storage.storageMap.put("apple", 10);
        purchaseHandler.doOperation("apple", 5);
        assertEquals(5, (int) Storage.storageMap.get("apple"));
    }
}
