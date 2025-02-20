package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 10);
        purchaseHandler.handleOperation(transaction);
        assertEquals(0, fruitsMap.get("apple"));
    }

    @Test
    void shouldNotAllowToBuyWhenStorageEmpty() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 5);
        purchaseHandler.handleOperation(fruitTransaction);
        assertNull(Storage.getFruits().get("apple"));
    }

    @Test
    void shouldNotAllowToBuyWhenNotEnoughInStock() {
        fruitsMap.put("apple", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 3);
        purchaseHandler.handleOperation(fruitTransaction);
        assertEquals(7, Storage.getFruits().get("apple"));
    }
}
