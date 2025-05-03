package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private FruitOperationHandler supplyOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    public void setUp() {
        inventory = new HashMap<>();
        supplyOperation = new SupplyOperation();
        inventory.put("banana", 5);
    }

    @Test
    @DisplayName("Add quantity to fruit - execute operation should update inventory correctly")
    public void addQuantityToFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 10);
        supplyOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(10, inventory.get("apple"));
    }

    @Test
    @DisplayName("Add quantity to existing fruit - execute operation should update inventory")
    public void addQuantityToExistingFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 10);
        supplyOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(15, inventory.get("banana"));
    }

    @Test
    @DisplayName("Add new fruit - execute operation should add fruit to inventory")
    public void addNewFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "cucumber", 20);
        supplyOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(20, inventory.get("cucumber"));
    }

    @Test
    @DisplayName("Supply zero quantity - execute operation should not change inventory")
    public void supplyZeroQuantity_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "cherry", 0);
        supplyOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(0, inventory.getOrDefault("cherry", 0));
    }

    @Test
    @DisplayName("Supply negative quantity - execute operation should throw exception")
    public void supplyNegativeQuantity_shouldThrowExceptiond_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "orange", -5);
        Assertions.assertThrows(RuntimeException.class, () ->
                supplyOperation.executeOperation(transaction, inventory));
    }

    @Test
    @DisplayName("Supply with null fruit name - should throw exception")
    public void supplyNullFruitName_shouldThrowException_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, null, 10);
        Assertions.assertThrows(RuntimeException.class, () ->
                supplyOperation.executeOperation(transaction, inventory));
    }
}
