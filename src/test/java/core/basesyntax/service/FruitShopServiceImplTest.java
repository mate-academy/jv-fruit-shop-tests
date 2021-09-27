package core.basesyntax.service;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.service.inter.FruitShopService;
import core.basesyntax.service.inter.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static List<FruitOperation> incorrectOperations;
    private static List<FruitOperation> correctOperations;
    private static Map<String, Operation> correctOperationsHandlers;
    private static Map<String, Operation> incorrectOperationsHandlers;
    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitShopService = new FruitShopServiceImpl();
        incorrectOperations = new ArrayList<>();
        //incorrectOperations.add(new FruitOperation("24", "apple", 25));
        //incorrectOperations.add(new FruitOperation("$6df", "banana", 62));
        //correctOperations = new ArrayList<>();
        //correctOperations.add(new FruitOperation("b", "apple", 86));
        //correctOperations.add(new FruitOperation("p", "banana", 35));
        //correctOperationsHandlers = new HashMap<>();
        //correctOperationsHandlers.put("b", new BalanceHandler());
        //correctOperationsHandlers.put("p", new PurchaseHandler());
    }

    ///...

    @Test (expected = RuntimeException.class)
    public void nullOperation() {
        fruitShopService.updateStorageInfo(null, correctOperationsHandlers);
    }

    @Test (expected = RuntimeException.class)
    public void nullOperationHandler() {
        fruitShopService.updateStorageInfo(correctOperations, null);
    }
}
