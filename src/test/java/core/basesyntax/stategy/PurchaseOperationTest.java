package core.basesyntax.stategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private FruitOperationHandler purchaseOperation;
    private Map<String, Integer> inventoryData;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
        inventoryData = new HashMap<>();
        inventoryData.put("apple", 10);
    }

    @Test
    void purchaseFruit_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 5);
        purchaseOperation.executeOperation(fruitTransaction, inventoryData);
        assertEquals(5, inventoryData.get("apple"));
    }

    @Test
    void purchaseOperation_throwsException_whenInsufficientInventory() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 20);
        assertThrows(RuntimeException.class, () ->
                purchaseOperation.executeOperation(fruitTransaction, inventoryData));
    }
}
