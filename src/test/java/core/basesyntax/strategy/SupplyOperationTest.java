package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private FruitOperationHandler supplyOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        inventory = new HashMap<>();
        supplyOperation = new SupplyOperation();
    }

    @Test
    void addQuantityToFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 10);
        supplyOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(10, inventory.get("apple"));
    }

    @Test
    void addQuantityToExistingFruit_executeOperation_Ok() {
        inventory.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 10);
        supplyOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(15, inventory.get("apple"));
    }

    @Test
    void addNewFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 20);
        supplyOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(20, inventory.get("banana"));
    }

    @Test
    void supplyZeroQuantity_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 0);
        supplyOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(0, inventory.getOrDefault("apple", 0));
    }

    @Test
    void supplyNegativeQuantity_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "orange", -5);
        Assertions.assertThrows(RuntimeException.class, () ->
                supplyOperation.executeOperation(transaction, inventory));
    }

    @Test
    void supplyNullFruitName_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, null, 10);
        Assertions.assertThrows(RuntimeException.class, () ->
                supplyOperation.executeOperation(transaction, inventory));
    }
}
