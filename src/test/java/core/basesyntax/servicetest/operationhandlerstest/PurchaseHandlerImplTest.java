package core.basesyntax.servicetest.operationhandlerstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.serviceimpl.operationhandlers.PurchaseHandlerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerImplTest {
    @Test
    void purchaseHandle_Ok() {
        Storage.fruitsAndAmount.put("banana", 50);
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 49));
        assertEquals(1, Storage.fruitsAndAmount.get("banana"));
    }

    @Test
    void balanceHandle_emptyStorage_NotOk() {
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        assertThrows(NullPointerException.class, () -> purchaseHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 100)));
    }

    @Test
    void handleNull_NotOk() {
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        assertThrows(
                NullPointerException.class, () -> purchaseHandler.handleOperation(null));
    }

    @Test
    void handle_notEnoughFruitsToPurchase_NotOk() {
        Storage.fruitsAndAmount.put("banana", 50);
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        assertThrows(RuntimeException.class, () -> purchaseHandler.handleOperation(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 51)));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsAndAmount.clear();
    }
}
