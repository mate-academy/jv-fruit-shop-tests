package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {

    private final ReturnOperationHandler handler = new ReturnOperationHandler();

    @Test
    void apply_returnToExistingFruit_shouldIncreaseQuantity() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 5);
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);

        handler.apply(transaction, storage);

        assertEquals(15, storage.get("apple"));
    }

    @Test
    void apply_returnToNewFruit_shouldAddNewEntry() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "banana", 7);
        Map<String, Integer> storage = new HashMap<>();

        handler.apply(transaction, storage);

        assertEquals(7, storage.get("banana"));
    }
}
