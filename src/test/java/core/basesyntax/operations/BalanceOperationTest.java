package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private final BalanceOperation balanceOperation = new BalanceOperation();
    private final Map<String, Integer> originalStorage = new HashMap<>(Storage.storage);

    @Test
    void dataWasAdded_Ok() {
        balanceOperation.getCalculation(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "peach", 5));
        assertTrue(Storage.storage.containsKey("peach"));
    }

    @AfterEach
    void returnOriginalData() {
        Storage.storage.clear();
        Storage.storage.putAll(originalStorage);
    }
}
