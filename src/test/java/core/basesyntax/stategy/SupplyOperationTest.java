package core.basesyntax.stategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private FruitOperationHandler supplyOperation;
    private Map<String, Integer> inventoryData;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        inventoryData = new HashMap<>();
        inventoryData.put("apple", 10);
    }

    @Test
    void supplyOperation_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "apple", 10);
        supplyOperation.executeOperation(fruitTransaction, inventoryData);
        assertEquals(20, inventoryData.get("apple"));
    }

    @Test
    void supplyOperationWithNegativeQuantity_throwsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "apple", -100);
        assertThrows(RuntimeException.class, () ->
                supplyOperation.executeOperation(fruitTransaction, inventoryData));
    }

    @Test
    void supplyOperationWhenFruitDoesNotExist_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "banana", 10);
        supplyOperation.executeOperation(fruitTransaction, inventoryData);
        assertEquals(10, inventoryData.get("banana"));
    }

    @Test
    void returnOperationZeroQuantity_trowsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "apple", 0);
        assertThrows(IllegalArgumentException.class, () ->
                supplyOperation.executeOperation(fruitTransaction, inventoryData));
    }
}
