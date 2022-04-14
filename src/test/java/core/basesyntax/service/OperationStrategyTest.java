package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.operation.AddOperation;
import core.basesyntax.service.operation.AddOrCreateOperation;
import core.basesyntax.service.operation.CreateOperation;
import core.basesyntax.service.operation.Operation;
import core.basesyntax.service.operation.RemoveOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<String, Operation> map;
    private static FruitsDao fruitsDao;
    private static Operation expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitsDao = new FruitsDaoImpl();

        map = new HashMap<>();
        map.put("b", new CreateOperation(fruitsDao));
        map.put("p", new RemoveOperation(fruitsDao));
        map.put("s", new AddOperation(fruitsDao));
        map.put("r", new AddOrCreateOperation(fruitsDao));
        operationStrategy = new OperationStrategyImpl(map);
    }

    @Test
    public void get_correctStrategy_Ok() {
        expected = new CreateOperation(fruitsDao);
        Operation actual = operationStrategy.get("b");
        assertEquals(expected.getClass(), actual.getClass());
        expected = new RemoveOperation(fruitsDao);
        actual = operationStrategy.get("p");
        assertEquals(expected.getClass(), actual.getClass());
        expected = new AddOperation(fruitsDao);
        actual = operationStrategy.get("s");
        assertEquals(expected.getClass(), actual.getClass());
        expected = new AddOrCreateOperation(fruitsDao);
        actual = operationStrategy.get("r");
        assertEquals(expected.getClass(), actual.getClass());
    }
}
