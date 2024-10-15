package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private FruitOperationHandler balanceOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        inventory = new HashMap<>();
        balanceOperation = new BalanceHandler();
    }

    @Test
    void addQuantityToFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 10);
        balanceOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(10, inventory.get("apple"));
    }

    @Test
    void addQuantityToExistingFruit_executeOperation_Ok() {
        inventory.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 10);
        balanceOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(15, inventory.get("apple"));
    }

    @Test
    void addSubtractionQuantityToNotExistingFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", -10);
        balanceOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(0, inventory.get("apple"));
    }
}
