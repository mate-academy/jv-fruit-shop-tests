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
    void addQuantityToFruit_executeOperation_NotOk() {
        inventory.put("orange", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "orange", 3);
        supplyOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(2, inventory.get("orange"));
    }
}
