package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    @Test
    void get_validOperation_Ok() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();

        OperationHandler balanceHandler = new BalanceOperation();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);

        OperationHandler actual =
                operationStrategy.get(FruitTransaction.Operation.BALANCE);

        assertSame(balanceHandler, actual);
    }

    @Test
    void get_operationNotFound_ReturnNull() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);

        OperationHandler actual =
                operationStrategy.get(FruitTransaction.Operation.BALANCE);

        assertNull(actual);
    }
}
