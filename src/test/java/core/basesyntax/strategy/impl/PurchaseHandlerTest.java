package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static OperationHandler purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    void executeOperationPurchaseHandlerValidData_OK() {
        FruitTransaction transactionPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", 100);
        Storage.storage.put(transactionPurchase.getFruit(), 150);
        purchaseOperation.executeOperation(transactionPurchase);
        int expectedQuantity = 50;
        int actualQuantity = Storage.storage
                        .get(transactionPurchase.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void executeOperationPurchaseOperationnotEnoughQuantity_NotOk() {
        FruitTransaction transactionPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", 100);
        Storage.storage.put(transactionPurchase.getFruit(), 50);
        assertThrows(RuntimeException.class, () -> purchaseOperation
                .executeOperation(transactionPurchase));
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}
