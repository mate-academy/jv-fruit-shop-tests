package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {

    @BeforeAll
    static void beforeAll() {
        Storage.storage.clear();
        Storage.storage.put("banana", 200);
    }

    @Test
    void handler_PurchaseCorrectWork_Ok() {
        PurchaseHandler purchaseHandler = new PurchaseHandler();
        purchaseHandler.execute("banana", 50);
        assertEquals(Storage.storage.get("banana"), 150);
    }

    @Test
    void handler_PurchaseMoreThanExist_Ok() {
        PurchaseHandler purchaseHandler = new PurchaseHandler();
        assertThrows(RuntimeException.class, () -> purchaseHandler.execute("banana", 500));
    }
}
