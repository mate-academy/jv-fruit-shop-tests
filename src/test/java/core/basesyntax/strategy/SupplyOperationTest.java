package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static SupplyOperation supplyOperation;
    private Map<String, Integer> inventory;

    @BeforeAll
    public static void beforeAll() {
        supplyOperation = new SupplyOperation();
    }

    @BeforeEach
    public void setUp() {
        inventory = new HashMap<>();
    }

    @Test
    public void handle_supplyOperation_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50);
        inventory.put("apple", 10);
        supplyOperation.handle(transaction, inventory);
        assertEquals(60, inventory.get("apple"));
    }
}
