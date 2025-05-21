package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final PurchaseOperation purchaseOperation = new PurchaseOperation();
    private final Map<String, Integer> originalStorage = new HashMap<>(Storage.storage);

    @BeforeEach
    void setUp() {
        Storage.storage.put("banana", 0);
    }

    @Test
    void invalidFruit_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            purchaseOperation.getCalculation(new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE,"invalidFruit",6));
        });
    }

    @Test
    void negativeValue_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            purchaseOperation.getCalculation(new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE,"banana",5));
        });
    }

    @AfterEach
    void returnOriginalData() {
        Storage.storage.clear();
        Storage.storage.putAll(originalStorage);
    }
}
