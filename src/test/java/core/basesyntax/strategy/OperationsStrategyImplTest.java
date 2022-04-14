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

public class OperationsStrategyImplTest {
    private static OperationsStrategy operationsStrategy;
    private static Map<Operations, Operation> operationMap;
    private static final Operations CORRECT_OPERATION = Operations.B;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationMap = new HashMap<>();
        operationMap.put(Operations.B, new BalanceOperation());
        operationMap.put(Operations.S, new SupplyOperation());
        operationMap.put(Operations.P, new PurchaseOperation());
        operationMap.put(Operations.R, new ReturnOperation());

        operationsStrategy = new OperationsStrategyImpl(operationMap);
    }

    @Test
    public void getTest_Ok() {
        Operation expected = operationMap.get(CORRECT_OPERATION);
        Operation actual = operationsStrategy.get(CORRECT_OPERATION);
        assertEquals(expected, actual);
    }
}
