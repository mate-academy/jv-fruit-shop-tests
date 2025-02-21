package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler;
    private Map<String, Integer> fruitsMap;

    @BeforeEach
    void setUp() {
        fruitsMap = new HashMap<>();
        Storage.setFruits(fruitsMap);
        purchaseHandler = new PurchaseHandler();
    }

    @Test
    void shouldDecreaseQuantity() {
        fruitsMap.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "apple", 10);

        purchaseHandler.handleOperation(transaction);
        assertEquals(0, fruitsMap.get("apple"));
    }

    @Test
    void shouldNotAllowToBuyWhenStorageEmpty() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "apple", 5);

        Exception exception = assertThrows(RuntimeException.class,
                () -> purchaseHandler.handleOperation(transaction));
        assertTrue(exception.getMessage().contains("Not enough in stock to sell"));
    }

    @Test
    void shouldNotAllowToBuyWhenNotEnoughInStock() {
        fruitsMap.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "apple", 3);

        purchaseHandler.handleOperation(transaction);
        assertEquals(7, Storage.getFruits().get("apple"));
    }

    @Test
    void shouldNotAllowNegativeQuantityPurchase() {
        fruitsMap.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "apple", -5);

        Exception exception = assertThrows(RuntimeException.class,
                () -> purchaseHandler.handleOperation(transaction));
        assertTrue(exception.getMessage().contains("Quantity must be greater than zero"));
    }

    @Test
    void shouldThrowExceptionWhenTransactionIsNull() {
        Exception exception = assertThrows(NullPointerException.class,
                () -> purchaseHandler.handleOperation(null));
        assertNotNull(exception);
    }

    @Test
    void shouldThrowExceptionWhenFruitNameIsNull() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                null, 5);

        Exception exception = assertThrows(RuntimeException.class,
                () -> purchaseHandler.handleOperation(transaction));
        assertTrue(exception.getMessage().contains("Fruit name cannot be null"));
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsZero() {
        fruitsMap.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                "apple", 0);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> purchaseHandler.handleOperation(transaction));
        assertTrue(exception.getMessage().contains("Quantity must be greater than zero"));
    }
}
