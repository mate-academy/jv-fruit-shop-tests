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
    private static final Integer NUM = 123;
    private static final Integer TO_BIG_NUM = 2_000_000_000;

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
        storageService.operation(null, "apple", NUM);
    }

    @Test(expected = RuntimeException.class)
    public void checkFruitIsNull_NotOK() {
        storageService.operation("b", null, NUM);
    }

    @Test(expected = RuntimeException.class)
    public void checkQuantityIsNull_NotOK() {
        storageService.operation("s", "banana", null);
    }

    @Test(expected = RuntimeException.class)
    public void operationMoreTwoLetters_NotOK() {
        storageService.operation("bs", "banana", NUM);
    }

    @Test(expected = RuntimeException.class)
    public void quantityToBig_NotOK() {
        storageService.operation("p", "pineapple", TO_BIG_NUM);
    }
}
