package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlersTest {
    private PurchaseOperationHandlers purchaseOperationHandlers;

    @BeforeEach
    void setUp() {
        purchaseOperationHandlers = new PurchaseOperationHandlers();
        FruitStorage.storage.clear();
    }

    @Test
    void handle_fruitExists_balanceOk_Ok() {
        String fruit = "apple";
        Integer initialQuantity = 10;
        Integer quantityToPurchase = 5;
        FruitStorage.storage.put(fruit, initialQuantity);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        fruit,
                        quantityToPurchase);
        purchaseOperationHandlers.handle(transaction);
        assertEquals(initialQuantity - quantityToPurchase, FruitStorage.storage.get(fruit));
    }

    @Test
    void handle_fruitExists_balanceBelowZero_notOk() {
        String fruit = "apple";
        Integer initialQuantity = 5;
        Integer quantityToPurchase = 10;
        FruitStorage.storage.put(fruit, initialQuantity);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        fruit,
                        quantityToPurchase);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandlers.handle(transaction);
        });
        assertEquals("Balance can`t be negative", exception.getMessage());
    }

    @Test
    void handle_fruitDoesNotExist_notOk() {
        String fruit = "orange";
        Integer quantityToPurchase = 5;
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        fruit,
                        quantityToPurchase);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandlers.handle(transaction);
        });
        assertEquals("Database does not contain: " + fruit, exception.getMessage());
    }
}
