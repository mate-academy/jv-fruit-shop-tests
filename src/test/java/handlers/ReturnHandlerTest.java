package handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.ReturnHandler;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
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
        operationHandler.operate(new FruitTransaction(Operation.RETURN, "banana", 5));
        Map<String, Integer> expected = Map.of("banana", 25);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }
}
