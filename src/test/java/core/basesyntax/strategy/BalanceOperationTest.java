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
    void setUp() {
        balanceOperation = new BalanceOperation();
        inventory = new HashMap<>();
    }

    @Test
    void apply_balance_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50);
        balanceOperation.apply(fruitTransaction, inventory);
        assertEquals(50, inventory.get("banana"));
    }
}
