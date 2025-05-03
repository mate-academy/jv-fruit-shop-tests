package core.basesyntax.service.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private final OperationHandler operationHandler = new SupplyHandler();

    @BeforeEach
    void preparation() {
        Storage.getStorage().clear();
    }

    @Test
    void process_validTransaction_Ok() {
        Storage.getStorage().clear();
        Storage.getStorage().put("apple", 100);
        operationHandler.process(new FruitTransaction(Operation.SUPPLY, "apple", 20));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 120);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }
}
