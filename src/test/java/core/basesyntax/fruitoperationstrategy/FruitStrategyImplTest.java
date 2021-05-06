package core.basesyntax.fruitoperationstrategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.operations.Operation;
import core.basesyntax.service.operationwithdata.AddOperation;
import core.basesyntax.service.operationwithdata.FruitOperationService;
import core.basesyntax.service.operationwithdata.SubtractOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStrategyImplTest {
    private static FruitStrategy fruitStrategy;
    private static Map<Operation, FruitOperationService> operationServiceMap;

    @BeforeClass
    public static void beforeClass() {
        operationServiceMap = new HashMap<>();
        operationServiceMap.put(Operation.RETURN, new AddOperation());
        operationServiceMap.put(Operation.BALANCE, new AddOperation());
        operationServiceMap.put(Operation.PURCHASE, new SubtractOperation());
        operationServiceMap.put(Operation.SUPPLY, new AddOperation());
        fruitStrategy = new FruitStrategyImpl(operationServiceMap);
    }

    @Test
    public void get_Ok() {
        FruitOperationService expectedOperationService = new AddOperation();
        FruitOperationService actualOperationService = fruitStrategy.get(Operation.BALANCE);
        assertEquals(expectedOperationService.getClass(), actualOperationService.getClass());
    }
}
