package core.basesyntax.strategy;

import core.basesyntax.dao.FruitMapDao;
import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.model.OperationType;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static FruitStorageDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitMapDao();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE.getOperation(),
                new AddOperation(fruitDao));
        operationHandlerMap.put(OperationType.PURCHASE.getOperation(),
                new RemoveOperation(fruitDao));
        operationHandlerMap.put(OperationType.SUPPLY.getOperation(),
                new AddOperation(fruitDao));
        operationHandlerMap.put(OperationType.RETURN.getOperation(),
                new AddOperation(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void operation_balance_Ok() {
        OperationHandler expected = operationHandlerMap.get(OperationType.BALANCE.getOperation());
        OperationHandler result = operationStrategy.get("b");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void operation_purchase_Ok() {
        OperationHandler expected = operationHandlerMap.get(OperationType.PURCHASE.getOperation());
        OperationHandler result = operationStrategy.get("p");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void operation_supply_Ok() {
        OperationHandler expected = operationHandlerMap.get(OperationType.SUPPLY.getOperation());
        OperationHandler result = operationStrategy.get("s");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void operation_return_Ok() {
        OperationHandler expected = operationHandlerMap.get(OperationType.RETURN.getOperation());
        OperationHandler result = operationStrategy.get("r");
        Assert.assertEquals(expected, result);
    }
}
