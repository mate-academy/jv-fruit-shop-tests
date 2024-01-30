package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.fruitshop.db.Storage;
import core.basesyntax.fruitshop.model.FruitTransaction;
import core.basesyntax.fruitshop.strategy.impl.PurchaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private final PurchaseHandler purchaseHandler = new PurchaseHandler();
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = Storage.getInstance();
        storage.getFruitStorage().clear();
        storage.getFruitStorage().put("apple", 20);
    }

    @Test
    void handlePurchaseOperation() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 10);
        purchaseHandler.handle(transaction);
        assertEquals(10, storage.getFruitStorage().get("apple").intValue(),
                "Purchase operation should decrease stock");
    }

    @Test
    void handlePurchaseOperationInsufficientStock() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 30);
        assertThrows(IllegalStateException.class, () -> purchaseHandler.handle(transaction),
                "Should throw exception for insufficient stock");
    }
}
