package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private FruitTransaction transaction;
    private final ShopStorage storage;
    private BalanceOperation balanceOperation;

    public BalanceOperationTest() {
        storage = new ShopStorage();
    }

    @BeforeEach
    void beforeEach() {
        storage.getStorage().clear();
        storage.add("apple", 30);
        transaction = FruitTransaction.of(FruitTransaction.Operation.BALANCE, "apple", 20);

        balanceOperation = new BalanceOperation();
    }

    @Test
    void handle_addOrResetFruits_ok() {
        balanceOperation.handle(transaction, storage);
        assertEquals(1, storage.getStorage().size());
        assertEquals(transaction.getQuantity(), storage.get(transaction.getFruit()));

        transaction = FruitTransaction.of(FruitTransaction.Operation.BALANCE, "banana", 5);
        balanceOperation.handle(transaction, storage);
        assertEquals(2, storage.getStorage().size());
        assertEquals(transaction.getQuantity(), storage.get(transaction.getFruit()));
    }
}
