package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {

    @Test
    void handle_addBalance_correctInventory() {
        BalanceOperation balanceOperation = new BalanceOperation();
        Map<String, Integer> inventory = new HashMap<>();
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50)
        );
        balanceOperation.handle(transactions, inventory);
        Assertions.assertEquals(2, inventory.size());
        Assertions.assertEquals(100, inventory.get("apple"));
        Assertions.assertEquals(50, inventory.get("banana"));
    }
}
