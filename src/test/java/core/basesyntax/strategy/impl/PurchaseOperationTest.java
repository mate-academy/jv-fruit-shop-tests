package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private FruitTransaction transaction;
    private final ShopStorage storage;
    private PurchaseOperation purchaseOperation;

    public PurchaseOperationTest() {
        storage = new ShopStorage();
    }

    @BeforeEach
    void beforeEach() {
        storage.getStorage().clear();
        storage.add("apple", 30);
        transaction = FruitTransaction.of(FruitTransaction.Operation.BALANCE, "apple", 20);

        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void handle_extractFruitQuantity_ok() {
        purchaseOperation.handle(transaction, storage);
        assertEquals(1, storage.getStorage().size());
        assertEquals(10, storage.get(transaction.getFruit()));
    }

    @Test
    void handle_quantityBelowZero_notOk() {
        storage.add("apple", 10);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.handle(transaction, storage));
    }
}
