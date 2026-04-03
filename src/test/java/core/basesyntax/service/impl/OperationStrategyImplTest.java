package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Storage storage;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        operationHandlers = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getHandler_validOperation_ok() {
        BalanceOperation handler = new BalanceOperation(storage);
        operationHandlers.put(FruitTransaction.Operation.BALANCE, handler);
        assertSame(handler, operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void getHandler_nullHandler() {
        assertNull(operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
    }
}
