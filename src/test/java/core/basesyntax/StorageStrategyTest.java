package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.StorageStrategy;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.StorageStrategyImpl;
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
    private static OperationStrategy operationStrategy;
    private static StorageStrategy storageStrategy;

    @BeforeClass
    public static void setUp() {
        Map<String, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put("s", new AdditionHandler());
        operationHandlers.put("b", new AdditionHandler());
        operationHandlers.put("r", new AdditionHandler());
        operationHandlers.put("p", new SubtractionHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        storageStrategy = new StorageStrategyImpl(operationStrategy);
    }

    @Before
    public void clean() {
        Storage.fruits.clear();
    }

    @Test
    public void storageStrategyWorks_Ok() {
        List<String[]> twoFruits = new ArrayList<>();
        twoFruits.add(new String[]{"s", "banana", "50"});
        twoFruits.add(new String[]{"p", "banana", "40"});
        storageStrategy.saveAll(twoFruits);
        assertTrue(Storage.fruits.get("banana").equals(10));
    }

    @Test
    public void storageStrategyMinusWorks_Ok() {
        List<String[]> twoFruits = new ArrayList<>();
        twoFruits.add(new String[]{"p", "banana", "50"});
        twoFruits.add(new String[]{"p", "banana", "40"});
        storageStrategy.saveAll(twoFruits);
        assertTrue(Storage.fruits.get("banana").equals(-90));
    }
}
