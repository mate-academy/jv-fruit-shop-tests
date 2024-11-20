package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {

    @Test
    void handle_addBalance_correctInventory() {
        BalanceOperation balanceOperation = new BalanceOperation();
        Map<String, Integer> inventory = new HashMap<>();
        FruitTransaction transaction1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
        FruitTransaction transaction2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50);
        balanceOperation.handle(transaction1, inventory);
        balanceOperation.handle(transaction2, inventory);
        assertEquals(2, inventory.size());
        assertEquals(100, inventory.get("apple"));
        assertEquals(50, inventory.get("banana"));
    }
}
