package core.basesyntax.service.impl.operations;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseOperationHandlerTest {
    PurchaseOperationHandler handler = new PurchaseOperationHandler();

    @AfterEach
    void afterEachTest() {
        Storage.clearStorage();
    }

    @Test
    void performOperation_nullFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> handler.performOperation(null, 10));
    }

    @Test
    void performOperation_emptyFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> handler.performOperation("", 10));
    }

    @Test
    void performOperation_negativeAmount_notOk() {
        assertThrows(RuntimeException.class, () -> handler.performOperation("banana", -2));
    }

    @Test
    void performOperation_noSuchFruitInStorage_notOk() {
        assertThrows(RuntimeException.class, () -> handler.performOperation("banana", 1));
    }

    @Test
    void performOperation_notEnoughFruits_notOk() {
        Storage.addFruits("banana", 10);
        assertThrows(RuntimeException.class, () -> handler.performOperation("banana", 11));
    }

    @Test
    void performOperation_ok() {
        Storage.addFruits("banana", 10);
        handler.performOperation("banana", 10);
        assertEquals(0, Storage.getFruitsBalance("banana"));

        Storage.addFruits("apple", 10);
        handler.performOperation("apple", 0);
        assertEquals(10, Storage.getFruitsBalance("apple"));
        handler.performOperation("apple", 1);
        assertEquals(9, Storage.getFruitsBalance("apple"));
    }
}