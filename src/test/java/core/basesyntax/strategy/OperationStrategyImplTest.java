package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.Operation;
import core.basesyntax.operations.Operations;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<Operations, Operation> operationMap;
    private static final Operations CORRECT_OPERATION = Operations.S;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationMap = new HashMap<>();
        operationMap.put(Operations.B, new BalanceOperation());
        operationMap.put(Operations.S, new SupplyOperation());
        operationMap.put(Operations.R, new ReturnOperation());
        operationMap.put(Operations.P, new PurchaseOperation());
        operationStrategy = new OperationStrategyImpl(operationMap);
    }

    @Test
    public void getStrategy_Ok() {
        Operation expected = operationMap.get(CORRECT_OPERATION);
        Operation actual = operationStrategy.get(CORRECT_OPERATION);
        assertEquals(expected, actual);
    }
}
