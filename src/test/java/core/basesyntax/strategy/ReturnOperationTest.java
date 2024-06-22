package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private ReturnOperation returnOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    public void setUp() {
        returnOperation = new ReturnOperation();
        inventory = new HashMap<>();
        inventory.put("banana", 30);
    }

    @Test
    public void handle_returnOperation_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20);
        returnOperation.handle(transaction, inventory);
        assertEquals(50, inventory.get("banana"));
    }

    @Test
    public void handle_returnOperationNegativeQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", -50);
        assertThrows(RuntimeException.class, () -> returnOperation.handle(transaction, inventory));
    }
}
