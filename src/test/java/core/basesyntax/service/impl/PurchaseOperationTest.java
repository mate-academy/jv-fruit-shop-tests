package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    public void setUp() {
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    public void handle() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "Apple", 5);
        Storage.quantities.put("Apple", 5);
        purchaseOperation.handle(transaction);
        assertEquals(5, Storage.quantities.get("Apple"));
    }

    @Test
    public void handle_newFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "Orange", 3);
        Storage.quantities.put("Orange", -3);
        purchaseOperation.handle(transaction);
        assertEquals(-3, Storage.quantities.get("Orange"));
    }

    @Test
    public void handle_quantityBecomesZero() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "Banana", 5);
        Storage.quantities.put("Banana", 0);
        purchaseOperation.handle(transaction);
        assertEquals(0, Storage.quantities.get("Banana"));
    }

    @Test
    public void handle_quantityBecomesNegative() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "Grapes", 10);
        Storage.quantities.put("Grapes", -5);
        purchaseOperation.handle(transaction);
        assertEquals(-5, Storage.quantities.get("Grapes"));
    }
}
