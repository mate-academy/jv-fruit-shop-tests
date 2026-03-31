package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static Storage storage;
    private static PurchaseOperation purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        purchaseOperation = new PurchaseOperation();
    }

    @BeforeEach
    void clearStorage() {
        storage.clear();
    }

    @Test
    void operateNotEnoughFruitsThrowRuntimeException() {
        storage.set("apple", 20);
        assertThrows(RuntimeException.class, () -> {
            purchaseOperation.operate(
                    new FruitTransaction("p", "apple", 100),
                    storage
            );
        });

    }

    @Test
    void operateValidData() {
        storage.set("apple", 120);
        purchaseOperation.operate(new FruitTransaction("p", "apple", 100), storage);

        assertEquals(20, storage.getQuantityByFruit("apple"));
    }
}
