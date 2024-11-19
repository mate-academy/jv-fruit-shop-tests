package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {

    @Test
    void handle_supplyItems_correctInventory_OK() {
        SupplyOperation supplyOperation = new SupplyOperation();
        Map<String, Integer> inventory = new HashMap<>(Map.of("apple", 50, "banana", 30));
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"apple", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"banana", 10)
        );
        supplyOperation.handle(transactions, inventory);
        Assertions.assertEquals(70, inventory.get("apple"));
        Assertions.assertEquals(40, inventory.get("banana"));
    }

}
