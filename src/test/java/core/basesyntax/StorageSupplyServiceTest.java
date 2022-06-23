package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.StorageSupplyService;
import core.basesyntax.service.impl.StorageSupplyServiceImpl;
import core.basesyntax.strategy.AdditionHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SubtractionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageSupplyServiceTest {
    private static Map<String, Integer> localTestStorage;
    private static StorageSupplyService supplyService;

    @BeforeClass
    public static void setUp() {
        Map<String, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put("s", new AdditionHandler());
        operationHandlers.put("b", new AdditionHandler());
        operationHandlers.put("r", new AdditionHandler());
        operationHandlers.put("p", new SubtractionHandler());
        localTestStorage = new HashMap<>();
        supplyService = new StorageSupplyServiceImpl(localTestStorage);
    }

    @After
    public void renovate() {
        localTestStorage = new HashMap<>();
        supplyService = new StorageSupplyServiceImpl(localTestStorage);
    }

    @Test
    public void storageSuppliesAddition_Ok() {
        supplyService.add("banana", 20);
        supplyService.add("banana", 20);
        assertTrue(localTestStorage.get("banana").equals(40));
    }

    @Test
    public void storageSuppliesSubtraction_Ok() {
        supplyService.add("banana", 40);
        supplyService.subtract("banana", 30);
        assertTrue(localTestStorage.get("banana").equals(10));
    }
}
