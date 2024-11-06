package core.basesyntax.stategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private FruitOperationHandler returnOperation;
    private Map<String, Integer> inventoryData;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        inventoryData = new HashMap<>();
        inventoryData.put("apple", 10);
    }

    @Test
    void returnOperation_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "apple", 10);
        returnOperation.executeOperation(fruitTransaction, inventoryData);
        assertEquals(20, inventoryData.get("apple"));
    }

    @Test
    void returnOperationWhenFruitDoesNotExist_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "banana", 10);
        returnOperation.executeOperation(fruitTransaction, inventoryData);
        assertEquals(10, inventoryData.get("banana"));
    }

    @Test
    void returnOperationZeroQuantity_trowsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "apple", 0);
        assertThrows(RuntimeException.class, () ->
                returnOperation.executeOperation(fruitTransaction, inventoryData));
    }

    @Test
    void returnOperationWithNegativeQuantity_trowsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "apple", -10);
        assertThrows(RuntimeException.class, () ->
                returnOperation.executeOperation(fruitTransaction, inventoryData));
    }
}
