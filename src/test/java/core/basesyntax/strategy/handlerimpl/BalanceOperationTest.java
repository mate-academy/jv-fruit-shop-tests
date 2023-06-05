package core.basesyntax.strategy.handlerimpl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BalanceOperationTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceOperationHandler_ok() {
        OperationHandler expectedHandler = new BalanceOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }
}
