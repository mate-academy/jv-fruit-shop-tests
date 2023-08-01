package core.basesyntax.service.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private final OperationHandler operationHandler = new PurchaseHandler();

    @BeforeEach
    void preparation() {
        Storage.getStorage().clear();
        Storage.getStorage().put("apple", 100);
    }

    @Test
    void process_validTransaction_Ok() {
        operationHandler.process(new FruitTransaction(Operation.PURCHASE, "apple", 20));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 80);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }

    @Test
    void process_invalidTransaction_throwRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.process(new FruitTransaction(Operation.PURCHASE, "apple", 120));
        });
        assertEquals("Can't do purchase operation. Count of fruit \"apple\" is 100. But "
                + "quantity of current purchase transaction is 120", exception.getMessage());
    }
}
