package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction();
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitsStorage.put("Fruit", 20);
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
    }

    @Test
    void testApplyPurchaseTransactionToStorage_Ok() {
        fruitTransaction.setQuantity(1);
        purchaseOperationHandler.applyTransactionToStorage(fruitTransaction);
    }

    @Test
    void testApplyPurchaseTransactionToStorage_NotOk() {
        fruitTransaction.setQuantity(-1);
        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.applyTransactionToStorage(fruitTransaction));
    }
}
