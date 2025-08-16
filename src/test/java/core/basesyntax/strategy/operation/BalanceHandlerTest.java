package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {

    @Test
    void applyBalance_Ok() {
        Map<String, Integer> storage = new HashMap<>();
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        OperationHandler handler = new BalanceHandler();
        handler.apply(fruitTransaction, storage);
        assertTrue(storage.containsKey("banana"));
    }
}
