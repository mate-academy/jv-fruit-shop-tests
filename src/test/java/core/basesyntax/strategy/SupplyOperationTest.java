package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private SupplyOperation supplyOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        inventory = new HashMap<>();
    }

    @Test
    void apply_purchase_ok() {
        inventory.put("banana", 20);

        FruitTransaction tr = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 5);

        supplyOperation.apply(tr, inventory);

        assertEquals(25, inventory.get("banana"));
    }
}
