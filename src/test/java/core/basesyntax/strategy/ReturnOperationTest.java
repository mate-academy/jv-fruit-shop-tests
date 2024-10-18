package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private FruitOperationHandler returnOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    public void setUp() {
        inventory = new HashMap<>();
        returnOperation = new ReturnOperation();

        inventory.put("banana", 5);
        inventory.put("orange", 5);
    }

    @Test
    @DisplayName("Add quantity to fruit - execute operation should update inventory correctly")
    public void addQuantityToFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 10);
        returnOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(10, inventory.get("apple"));
    }

    @Test
    @DisplayName("Add quantity to existing fruit - execute operation should update inventory")
    public void addQuantityToExistingFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "banana", 10);
        returnOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(15, inventory.get("banana"));
    }

    @Test
    @DisplayName("Return zero quantity - execute operation should not change inventory")
    public void returnZeroQuantity_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "cherry", 0);
        returnOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(0, inventory.getOrDefault("cherry", 0));
    }

    @Test
    @DisplayName("Return negative quantity - execute operation should throw exception")
    void returnNegativeQuantity_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "fruit", -5);

        Assertions.assertThrows(RuntimeException.class, () ->
                returnOperation.executeOperation(transaction, inventory));
    }

    @Test
    @DisplayName("Return to non-existent fruit - execute operation should add fruit to inventory")
    public void returnToNonExistentFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "cucumber", 10);
        returnOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(10, inventory.get("cucumber"));
    }

    @Test
    @DisplayName("Return exceeding quantity - execute operation should update inventory correctly")
    public void returnExceedingQuantity_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "orange", 10);
        returnOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(15, inventory.get("orange"));
    }

    @Test
    @DisplayName("Return with null transaction - should throw NullPointerException")
    public void returnWithNullTransaction_shouldThrowException_Ok() {
        Assertions.assertThrows(NullPointerException.class, () ->
                returnOperation.executeOperation(null, inventory));
    }

    @Test
    @DisplayName("Return with null fruit name - should throw exception")
    public void returnWithNullFruitName_shouldThrowException_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, null, 10);

        Assertions.assertThrows(RuntimeException.class, () ->
                returnOperation.executeOperation(transaction, inventory));
    }
}
