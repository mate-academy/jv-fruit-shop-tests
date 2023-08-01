package core.basesyntax.service.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private final OperationHandler operationHandler = new ReturnHandler();

    @Test
    void process_validTransaction_noException() {
        Storage.getStorage().put("apple", 100);
        operationHandler.process(new FruitTransaction(Operation.RETURN, "apple", 10));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 110);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }

}
