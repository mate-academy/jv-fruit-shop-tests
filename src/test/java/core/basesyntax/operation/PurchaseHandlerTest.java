package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private ShopService shopService;
    private PurchaseHandler purchaseHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        purchaseHandler = new PurchaseHandler(shopService);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 100);
        shopService.addFruits("apple", 150);
    }

    @Test
    void apply_purchaseTransaction_success() {
        purchaseHandler.apply(fruitTransaction);
        int expectedQuantity = 50;
        int actualQuantity = shopService.getQuantity("apple");
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void apply_purchaseTransaction_unsuccessful() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 200);
        shopService.addFruits("apple", 100);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseHandler.apply(fruitTransaction);
        });
        assertEquals("Not enough apple in stock for purchase", exception.getMessage());
    }

    @Test
    void apply_nonPurchaseOperation_throwsRuntimeException() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseHandler.apply(fruitTransaction);
        });
        assertEquals("Unsupported operation for PurchaseHandler", exception.getMessage());
    }
}
