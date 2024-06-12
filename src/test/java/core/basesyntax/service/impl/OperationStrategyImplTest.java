package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private OperationHandler balanceHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperation();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);

        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void test_get_operationBalance_returnsHandler() {
        assertEquals(balanceHandler, operationStrategy.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void test_get_unknownOperation_returnsNull() {
        assertNull(operationStrategy.get(null));
    }

    @Test
    void test_get_nonExistingOperation_returnsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.get(FruitTransaction.Operation.fromCode("d")));
    }

    @Test
    void test_get_emptyMap_throwsException() {
        assertThrows(RuntimeException.class, () -> new OperationStrategyImpl(new HashMap<>()));
    }
}
