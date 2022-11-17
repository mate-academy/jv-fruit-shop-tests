package core.basesyntax.service;

import core.basesyntax.service.operations.Balance;
import core.basesyntax.service.operations.IOperation;
import core.basesyntax.service.operations.Purchase;
import core.basesyntax.service.operations.Return;
import core.basesyntax.service.operations.Supply;
import core.basesyntax.strategy.FruitStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageServiceTest {
    private static StorageService storageService;

    @BeforeClass
    public static void initStorageServiceClass() {
        Map<String, IOperation> listOperations = new HashMap<>();
        listOperations.put("b", new Balance());
        listOperations.put("s", new Supply());
        listOperations.put("p", new Purchase());
        listOperations.put("r", new Return());
        storageService = new StorageService(new FruitStrategy(listOperations));
    }

    @Test(expected = RuntimeException.class)
    public void checkOperationIsNull_NotOK() {
        storageService.operation(null, "apple", 100);
    }

    @Test(expected = RuntimeException.class)
    public void checkFruitIsNull_NotOK() {
        storageService.operation("b", null, 100);
    }

    @Test(expected = RuntimeException.class)
    public void checkQuantityIsNull_NotOK() {
        storageService.operation("s", "banana", null);
    }
}
