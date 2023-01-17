package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerStrategyImplTest {
    private static final String NOT_CORRECT_OPERATION_TYPE = "c";
    private static Map<String, OperationHandler> operationHandler = new HashMap<>();
    private static OperationHandlerStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        operationStrategy = new OperationHandlerStrategyImpl(operationHandler);
    }

    @Test(expected = RuntimeException.class)
    public void getOperation_notCorrectType_notOk() {
        operationStrategy.get(String.valueOf(FruitTransaction.FruitOperation
                .valueOf(NOT_CORRECT_OPERATION_TYPE)));
    }

    @Test(expected = RuntimeException.class)
    public void getOperation_nullData_notOk() {
        operationStrategy.get(String.valueOf(FruitTransaction.FruitOperation.valueOf(null)));
    }
}

