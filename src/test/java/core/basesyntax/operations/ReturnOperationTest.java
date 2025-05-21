package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private final ReturnOperation returnOperation = new ReturnOperation();
    private final Map<String, Integer> originalStorage = new HashMap<>(Storage.storage);

    @Test
    void invalidFruit_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            returnOperation.getCalculation(new FruitTransaction(
                    FruitTransaction.Operation.RETURN,"invalidFruit",6));
        });
    }

    @Test
    void dataWasAdded_Ok() {
        Storage.storage.clear();
        Storage.storage.put("banana", 0);
        returnOperation.getCalculation(new FruitTransaction(
                FruitTransaction.Operation.RETURN,"banana",3));
        assertEquals(3, Storage.storage.get("banana"));
    }

    @AfterEach
    void returnOriginalData() {
        Storage.storage.clear();
        Storage.storage.putAll(originalStorage);
    }
}
