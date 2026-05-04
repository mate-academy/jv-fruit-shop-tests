package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private final OperationHandler operationHandler = new PurchaseOperationHandler();

    @AfterEach
    void clearStorage() {
        Storage.getStorage().clear();
    }

    @Test
    void apply_purchaseHandler_ok() {
        Storage.setStorage("banana", 30);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 20);

        operationHandler.apply(transaction);

        assertEquals(10, Storage.getStorage().get("banana"));
    }

    @Test
    void apply_notEnoughFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 20);

        assertThrows(IllegalArgumentException.class, () -> operationHandler.apply(transaction));
    }
}
