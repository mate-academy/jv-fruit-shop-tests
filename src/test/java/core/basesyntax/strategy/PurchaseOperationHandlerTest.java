package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;

    @BeforeAll
    static void setUp() {
        purchaseHandler = new PurchaseOperationHandler();
    }

    @BeforeEach
    void setStorageUpBefore() {
        Storage.getStorage().put("apple", 10);
    }

    @AfterEach
    void cleanUp() {
        Storage.getStorage().clear();
    }

    @Test
    void handle_ValidData_Ok() {
        purchaseHandler.handle("apple", 7);
        assertEquals(3, (int) Storage.getStorage().get("apple"));
    }

    @Test
    void handle_NegativeData_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle("apple", -10));
        String expected = "The quantity must be positive";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void sell_MoreThan_Has() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle("apple", 11));
        String expected = "You can't sell more than you have";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void handle_NullFruit_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(null, 10));
    }

    @Test
    void handle_ZeroData_Ok() {
        purchaseHandler.handle("apple", 0);
        assertEquals(10, (int) Storage.getStorage().get("apple"));
    }
}
