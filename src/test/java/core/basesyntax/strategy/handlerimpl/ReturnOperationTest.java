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

public class ReturnOperationTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_returnOperationHandler_ok() {
        OperationHandler expectedHandler = new ReturnOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }
}
