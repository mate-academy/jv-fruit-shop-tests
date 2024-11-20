package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {

    @Test
    void handle_supplyItems_correctInventory_OK() {
        SupplyOperation supplyOperation = new SupplyOperation();
        Map<String, Integer> inventory = new HashMap<>(Map.of("apple", 50, "banana", 30));
        FruitTransaction transaction1 =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"apple", 20);
        FruitTransaction transaction2 =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"banana", 10);
        supplyOperation.handle(transaction1, inventory);
        supplyOperation.handle(transaction2, inventory);
        assertEquals(70, inventory.get("apple"));
        assertEquals(40, inventory.get("banana"));
    }

}
