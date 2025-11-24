package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private FruitTransaction transaction;
    private final ShopStorage storage;
    private SupplyOperation supplyOperation;

    public SupplyOperationTest() {
        storage = new ShopStorage();
    }

    @BeforeEach
    void beforeEach() {
        storage.getStorage().clear();
        storage.add("apple", 30);
        transaction = FruitTransaction.of(FruitTransaction.Operation.BALANCE, "apple", 20);

        supplyOperation = new SupplyOperation();
    }

    @Test
    void handle_addToFruitQuantity_ok() {
        supplyOperation.handle(transaction, storage);
        assertEquals(1, storage.getStorage().size());
        assertEquals(50, storage.get(transaction.getFruit()));
    }
}
