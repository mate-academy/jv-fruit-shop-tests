package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String APPLE = "Apple";
    private static final String BANANA = "Banana";
    private static final String ORANGE = "Orange";
    private static final String GRAPES = "Grapes";
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    public void setUp() {
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    public void handle() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                APPLE, 5);
        Storage.quantities.put(APPLE, 5);
        purchaseOperation.handle(transaction);
        assertEquals(0, Storage.quantities.get(APPLE));
    }

    @Test
    public void handle_newFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                ORANGE, 3);
        Storage.quantities.put(ORANGE, -3);
        purchaseOperation.handle(transaction);
        assertEquals(-6, Storage.quantities.get(ORANGE));
    }

    @Test
    public void handle_quantityIsZero() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                BANANA, 5);
        Storage.quantities.put(BANANA, 0);
        purchaseOperation.handle(transaction);
        assertEquals(-5, Storage.quantities.get(BANANA));
    }

    @Test
    public void handle_quantityIsNegative() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                GRAPES, 10);
        Storage.quantities.put(GRAPES, -5);
        purchaseOperation.handle(transaction);
        assertEquals(-15, Storage.quantities.get(GRAPES));
    }
}
