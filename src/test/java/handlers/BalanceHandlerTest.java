package handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.handler.BalanceHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceHandler();
    }

    @Test
    void testOperateBalance_ok() {
        operationHandler.operate(new FruitTransaction(Operation.BALANCE, "banana", 50));
        Map<String, Integer> expected = Map.of("banana", 50);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }
}
