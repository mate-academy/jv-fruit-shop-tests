package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnHandler();
        Storage.storage.put("banana", 20);
    }

    @Test
    void testOperateReturn_ok() {
        operationHandler.operate(new FruitTransaction(FruitTransaction.OperationType.RETURN,
                "banana", 5));
        Map<String, Integer> expected = Map.of("banana", 25);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }
}
