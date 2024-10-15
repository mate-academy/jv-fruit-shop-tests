package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private FruitOperationHandler returnOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        inventory = new HashMap<>();
        returnOperation = new ReturnOperation();
    }

    @Test
    void addQuantityToFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 10);
        returnOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(10, inventory.get("apple"));
    }

    @Test
    void addQuantityToExistingFruit_executeOperation_Ok() {
        inventory.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 10);
        returnOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(15, inventory.get("apple"));
    }

    @Test
    void returnZeroQuantity_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 0);
        returnOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(0, inventory.getOrDefault("apple", 0));
    }

    @Test
    void returnNegativeQuantity_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", -5);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                returnOperation.executeOperation(transaction, inventory));
    }

    @Test
    void returnToNonExistentFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "banana", 10);
        returnOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(10, inventory.get("banana"));
    }

    @Test
    void returnExceedingQuantity_executeOperation_Ok() {
        inventory.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 10);
        returnOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(15, inventory.get("apple"));
    }

    @Test
    void returnWithNullTransaction_executeOperation_Ok() {
        Assertions.assertThrows(NullPointerException.class, () ->
                returnOperation.executeOperation(null, inventory));
    }

    @Test
    void returnWithNullFruitName_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, null, 10);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                returnOperation.executeOperation(transaction, inventory));
    }
}
