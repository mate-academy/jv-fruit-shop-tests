package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private SupplyOperation supplyOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    public void setUp() {
        supplyOperation = new SupplyOperation();
        inventory = new HashMap<>();
    }

    @Test
    public void handle_supplyOperation_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 150);
        supplyOperation.handle(transaction, inventory);
        assertEquals(150, inventory.get("banana"));
    }

    @Test
    public void handle_supplyOperationNegativeQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", -50);
        assertThrows(RuntimeException.class, () -> supplyOperation.handle(transaction, inventory));
    }
}
