package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.Operation;
import core.basesyntax.operations.Operational;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import core.basesyntax.service.OperationsExecutorService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationsExecutorServiceImplTest {
    private static final List<String[]> OPERATION_LIST = new ArrayList<>();
    private static final Map<String, Integer> EXPECTED_MAP = new HashMap<>();
    private static OperationsExecutorService operationsExecutorService;

    @BeforeClass
    public static void beforeClass() {
        final Map<String, Operational> operationMap = new HashMap<>();
        operationMap.put(Operation.SUPPLY.getOperation(), new SupplyOperation());
        operationMap.put(Operation.RETURN.getOperation(), new ReturnOperation());
        operationMap.put(Operation.BALANCE.getOperation(), new BalanceOperation());
        operationMap.put(Operation.PURCHASE.getOperation(), new PurchaseOperation());
        OperationStrategy fruitOperationStrategy = new OperationStrategyImpl(operationMap);
        operationsExecutorService =
                new OperationsExecutorServiceImpl(fruitOperationStrategy);
        OPERATION_LIST.add(new String[]{"b", "apple", "10"});
        OPERATION_LIST.add(new String[]{"b", "orange", "5"});
        OPERATION_LIST.add(new String[]{"s", "apple", "5"});
        OPERATION_LIST.add(new String[]{"s", "apple", "15"});
        OPERATION_LIST.add(new String[]{"p", "apple", "10"});
        EXPECTED_MAP.put("apple", 20);
        EXPECTED_MAP.put("orange", 5);
    }

    @Test
    public void executeOperations_ok() {
        operationsExecutorService.executeOperations(OPERATION_LIST);
        assertEquals(EXPECTED_MAP, Storage.fruitStorage);
    }

    @Test(expected = RuntimeException.class)
    public void executeOperationsWithNullList() {
        operationsExecutorService.executeOperations(null);
    }
}
