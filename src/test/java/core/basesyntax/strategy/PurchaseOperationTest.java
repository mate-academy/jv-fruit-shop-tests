package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private ShopService shopService;
    private PurchaseOperation purchase;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        purchase = new PurchaseOperation(shopService);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 100);
        shopService.addFruits("apple", 150);
    }

    @Test
    void apply_purchaseTransaction_success() {
        purchase.handle(fruitTransaction);
        int expectedQuantity = 50;
        int actualQuantity = shopService.getQuantity("apple");
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void apply_purchaseTransaction_unsuccessful() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 200);
        shopService.setFruits("apple", 100);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchase.handle(fruitTransaction);
        });
        assertEquals("Not enough apple in stock for purchase", exception.getMessage());
    }

    @Test
    void apply_nonPurchaseOperation_throwsRuntimeException() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchase.handle(fruitTransaction);
        });
        assertEquals("Unsupported operation for PurchaseHandler", exception.getMessage());
    }
}
