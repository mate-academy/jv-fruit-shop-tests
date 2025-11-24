package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private FruitTransaction transaction;
    private final ShopStorage storage;
    private ReturnOperation returnOperation;

    public ReturnOperationTest() {
        storage = new ShopStorage();
    }

    @BeforeEach
    void beforeEach() {
        storage.getStorage().clear();
        storage.add("apple", 30);
        transaction = FruitTransaction.of(FruitTransaction.Operation.BALANCE, "apple", 20);

        returnOperation = new ReturnOperation();
    }

    @Test
    void handle_addToFruitQuantity_ok() {
        returnOperation.handle(transaction, storage);
        assertEquals(1, storage.getStorage().size());
        assertEquals(50, storage.get(transaction.getFruit()));
    }
}
