package core.basesyntax;

import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.OperationType;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrategyTest {
    private static Map<OperationType, OperationHandler> operationHandlerMap;
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @BeforeEach
    void setUp() {
        operationHandlerMap.put(OperationType.B, new BalanceOperationHandler());
        operationHandlerMap.put(OperationType.S, new SupplyOperationHandler());
        operationHandlerMap.put(OperationType.P, new PurchaseOperationHandler());
        operationHandlerMap.put(OperationType.R, new ReturnOperationHandler());
    }

    @Test
    void testStrategyWithCorrectOperations_Ok() {
        Assertions.assertEquals(BalanceOperationHandler.class,
                strategy.get(OperationType.B).getClass());
        Assertions.assertEquals(SupplyOperationHandler.class,
                strategy.get(OperationType.S).getClass());
        Assertions.assertEquals(PurchaseOperationHandler.class,
                strategy.get(OperationType.P).getClass());
        Assertions.assertEquals(ReturnOperationHandler.class,
                strategy.get(OperationType.R).getClass());
    }

    @Test
    void testStrategyWithNonExistingOperation_NotOk() {
        operationHandlerMap.remove(OperationType.R);
        Assertions.assertThrows(RuntimeException.class, () -> strategy.get(OperationType.R));
    }
}
