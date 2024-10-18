package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private FruitOperationHandler balanceOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    public void setUp() {
        inventory = new HashMap<>();
        balanceOperation = new BalanceHandler();

        inventory.put("orange", 5);
    }

    @Test
    @DisplayName("Set quantity of a new fruit - execute operation should set balance correctly")
    public void addQuantityToFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 10);
        balanceOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(10, inventory.get("apple"));
    }

    @Test
    @DisplayName("Set quantity of an existing fruit - "
            + "execute operation should override previous balance")
    public void addQuantityToExistingFruit_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "orange", 10);
        balanceOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(10, inventory.get("orange"));
    }

    @Test
    @DisplayName("Set negative quantity - execute operation should throw exception")
    public void setNegativeQuantity_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", -5);

        Assertions.assertThrows(RuntimeException.class, () ->
                balanceOperation.executeOperation(transaction, inventory));
    }
}
