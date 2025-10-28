package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {

    private final SupplyOperationHandler handler = new SupplyOperationHandler();

    @Test
    void apply_validSupplyTransaction_shouldIncreaseQuantity() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 40);
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);

        handler.apply(transaction, storage);

        assertEquals(50, storage.get("apple"));
    }

    @Test
    void apply_newFruit_shouldAddToStorage() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 25);
        Map<String, Integer> storage = new HashMap<>();

        handler.apply(transaction, storage);

        assertEquals(25, storage.get("banana"));
    }
}
