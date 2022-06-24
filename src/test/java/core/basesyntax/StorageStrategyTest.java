package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.StorageStrategy;
import core.basesyntax.service.StorageSupplyService;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.StorageStrategyImpl;
import core.basesyntax.service.impl.StorageSupplyServiceImpl;
import core.basesyntax.strategy.AdditionHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SubtractionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageStrategyTest {
    private static Storage localStorage;
    private static StorageSupplyService storageSupplyService;
    private static OperationStrategy operationStrategy;
    private static StorageStrategy storageStrategy;

    @BeforeClass
    public static void setUp() {
        Map<String, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put("s", new AdditionHandler());
        operationHandlers.put("b", new AdditionHandler());
        operationHandlers.put("r", new AdditionHandler());
        operationHandlers.put("p", new SubtractionHandler());
        localStorage = new Storage();
        storageSupplyService = new StorageSupplyServiceImpl(localStorage);
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        storageStrategy = new StorageStrategyImpl(operationStrategy);
    }

    @Before
    public void clean() {
        localStorage.flush();
    }

    @Test
    public void storageStrategyWorks_Ok() {
        List<String[]> fruits = new ArrayList<>();
        fruits.add(new String[]{"s", "banana", "50"});
        fruits.add(new String[]{"p", "banana", "40"});
        storageStrategy.saveAll(fruits, storageSupplyService);
        assertTrue(localStorage.getFruitsInStorage().get("banana").equals(10));
    }

    @Test
    public void storageStrategyMinusWorks_Ok() {
        List<String[]> fruits = new ArrayList<>();
        fruits.add(new String[]{"p", "banana", "50"});
        fruits.add(new String[]{"p", "banana", "40"});
        storageStrategy.saveAll(fruits, storageSupplyService);
        assertTrue(localStorage.getFruitsInStorage().get("banana").equals(-90));
    }
}
