package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    public void setUp() {
        balanceOperation = new BalanceOperation();
        inventory = new HashMap<>();
    }

    @Test
    public void handle_balanceOperation_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
        balanceOperation.handle(transaction, inventory);
        assertEquals(100, inventory.get("apple"));
    }
}
