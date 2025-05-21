package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private final SupplyOperation supplyOperation = new SupplyOperation();
    private final Map<String, Integer> originalStorage = new HashMap<>(Storage.storage);

    @Test
    void invalidFruit_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            supplyOperation.getCalculation(new FruitTransaction(
                    FruitTransaction.Operation.SUPPLY,"invalidFruit",6));
        });
    }

    @Test
    void dataWasAdded_Ok() {
        Storage.storage.clear();
        Storage.storage.put("apple", 0);
        supplyOperation.getCalculation(new FruitTransaction(
                FruitTransaction.Operation.RETURN,"apple",5));
        assertEquals(5, Storage.storage.get("apple"));
    }

    @AfterEach
    void returnOriginalData() {
        Storage.storage.clear();
        Storage.storage.putAll(originalStorage);
    }
}
