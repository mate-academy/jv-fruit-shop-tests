package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private Storage storage;
    private OperationHandler purchaseOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage(new HashMap<>());
        storage.addFruit("kiwi", 100);
        storage.addFruit("apple", 50);
        storage.addFruit("orange", 20);
        storage.addFruit("pear", 10);
        purchaseOperation = new PurchaseOperation(storage);
    }

    @Test
    void handle_notFoundFruitInStorage_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(1);
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.handle(transaction));
    }

    @Test
    void handle_currentQuantityLessThanRequired_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("kiwi");
        transaction.setQuantity(101);
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.handle(transaction));
    }

    @Test
    void handle_normalOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("kiwi");
        transaction.setQuantity(100);
        purchaseOperation.handle(transaction);
        assertEquals(0, storage.getFruitQuantity("kiwi"));
    }
}
