package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
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
        operationHandler.operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                "banana", 50));
        Map<String, Integer> expected = Map.of("banana", 50);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }
}
