package core.basesyntax.stategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private FruitOperationHandler balanceHandler;
    private Map<String, Integer> inventoryData;

    @BeforeEach
    void setUp() {
        inventoryData = new HashMap<>();
        balanceHandler = new BalanceHandler();
        inventoryData.put("apple", 10);
    }

    @Test
    void addQuantity_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "banana", 10);
        balanceHandler.executeOperation(fruitTransaction, inventoryData);
        assertEquals(10, inventoryData.get("banana"));
    }

    @Test
    void addQuantityToExistingFruit_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "apple", 15);
        balanceHandler.executeOperation(fruitTransaction, inventoryData);
        assertEquals(15, inventoryData.get("apple"));
    }

    @Test
    void setNegativeBalance_trowsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "banana", -10);
        assertThrows(RuntimeException.class, () ->
                balanceHandler.executeOperation(fruitTransaction, inventoryData));
    }
}
