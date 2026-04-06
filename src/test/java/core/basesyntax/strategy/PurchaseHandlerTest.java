package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private Storage storage;
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        purchaseHandler = new PurchaseHandler(storage);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_validQuantity_ok() {
        storage.add("banana", 20);
        purchaseHandler.handle("banana", 5);
        assertEquals(15, (int) storage.get("banana"));
    }

    @Test
    void handle_insufficientBalance_notOk() {
        storage.add("banana", 3);
        assertThrows(RuntimeException.class, () -> purchaseHandler.handle("banana", 10));
    }

    @Test
    void handle_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () -> purchaseHandler.handle(null, 10));
    }
}
