package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation()
        );
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getHandler_validOperation_returnHandler() {
        assertNotNull(operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void getHandler_invalidOperation_throwsException() {
        assertThrows(RuntimeException.class, () ->
                operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE));
    }
}
