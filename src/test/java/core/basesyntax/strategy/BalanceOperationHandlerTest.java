package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {

    @AfterEach
    void clear() {
        Storage.storage.clear();
    }

    @Test
    void when_ValidBalance_Ok() {
        Map<String, Integer> expected = Map.of("banana", 100, "apple", 15);
        OperationHandler balanceOperation = new BalanceOperationHandler();
        FruitTransaction bananaBalance = new FruitTransaction(
                Operation.BALANCE,
                "banana",
                100);
        FruitTransaction appleBalance = new FruitTransaction(
                Operation.BALANCE,
                "apple",
                15);
        balanceOperation.processOperation(bananaBalance);
        balanceOperation.processOperation(appleBalance);
        assertEquals(expected, Storage.storage);
    }
}
