package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {

    @AfterEach
    void clear() {
        Storage.storage.clear();
    }

    @Test
    void when_SupplyOfExisting_Ok() {
        Map<String, Integer> expected = Map.of("banana", 100);
        Storage.storage.put("banana", 0);
        OperationHandler supplyOperation = new SupplyOperationHandler();
        FruitTransaction supply = new FruitTransaction(
                Operation.SUPPLY,
                "banana",
                100
        );
        supplyOperation.processOperation(supply);
        assertEquals(expected, Storage.storage);
    }

    @Test
    void when_SupplyOfNonExisting_Ok() {
        Map<String, Integer> expected = Map.of("banana", 100, "apple", 100);
        Storage.storage.put("banana", 100);
        OperationHandler supplyOperation = new SupplyOperationHandler();
        FruitTransaction supply = new FruitTransaction(
                Operation.SUPPLY,
                "apple",
                100
        );
        supplyOperation.processOperation(supply);
        assertEquals(expected, Storage.storage);
    }
}
