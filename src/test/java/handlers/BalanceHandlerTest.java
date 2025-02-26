package handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.handler.BalanceHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceHandler();
    }

    @AfterEach
    void clear() {
        Storage.storage.clear();
    }

    @Test
    void testOperateBalance_ok() {
        operationHandler.operate(new FruitTransaction(Operation.BALANCE, "banana", 50));
        Map<String, Integer> expected = Map.of("banana", 50);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @Test
    void testOperateBalanceBanana_ok() {
        operationHandler.operate(new FruitTransaction(Operation.BALANCE, "banana", 30));
        operationHandler.operate(new FruitTransaction(Operation.BALANCE, "banana", 70));
        int expectedBananas = 70;
        int actualBananas = Storage.storage.get("banana");
        assertEquals(expectedBananas, actualBananas);
    }
}
