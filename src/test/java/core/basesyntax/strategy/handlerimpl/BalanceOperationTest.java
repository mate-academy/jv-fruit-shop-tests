package core.basesyntax.strategy.handlerimpl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BalanceOperationTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private BalanceOperation balanceOperation;

    @Before
    public void setUp() {
        balanceOperation = new BalanceOperation();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceOperationHandler_ok() {
        OperationHandler expectedHandler = new BalanceOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }
}
