package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> handlersMap = new HashMap<>();
        handlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationStrategy = new OperationStrategyImpl(handlersMap);
    }

    @Test
    void get_validOperation_Ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertNotNull(handler);
    }

    @Test
    void constructor_nullMap_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OperationStrategyImpl(null);
        });
    }

    @Test
    void get_nullOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            operationStrategy.get(null);
        });
    }

    @Test
    void get_nonExistentOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        });
    }
}
