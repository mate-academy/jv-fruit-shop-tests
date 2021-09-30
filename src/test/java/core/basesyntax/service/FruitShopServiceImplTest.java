package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.service.handler.BalanceHandler;
import core.basesyntax.service.handler.PurchaseHandler;
import core.basesyntax.service.handler.ReturnHandler;
import core.basesyntax.service.handler.SupplyHandler;
import core.basesyntax.service.inter.FruitOperationListCreateService;
import core.basesyntax.service.inter.FruitShopService;
import core.basesyntax.service.inter.Operation;
import core.basesyntax.service.inter.ReadService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static List<FruitOperation> correctOperations;
    private static Map<String, Operation> correctOperationsHandlers;
    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitShopService = new FruitShopServiceImpl();
        correctOperationsHandlers = new HashMap<>();
        correctOperationsHandlers.put("b", new BalanceHandler());
        correctOperationsHandlers.put("p", new PurchaseHandler());
        correctOperationsHandlers.put("r", new ReturnHandler());
        correctOperationsHandlers.put("s", new SupplyHandler());
        ReadService readService = new ReadServiceImpl();
        List<String> strings = readService.readFromFile("src/test/java/resources/input.csv");
        FruitOperationListCreateService fruitOperationListCreateService
                = new FruitOperationListCreateServiceImpl();
        correctOperations = fruitOperationListCreateService.getFruitOperationsList(strings);
    }

    @Test
    public void updateStorage_Ok() {
        fruitShopService.updateStorageInfo(correctOperations, correctOperationsHandlers);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 90);
        expected.put("banana", 152);
        Map<String, Integer> actual = Storage.fruitsQuantity;
        System.out.println(expected);
        System.out.println(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void nullOperation() {
        fruitShopService.updateStorageInfo(null, correctOperationsHandlers);
    }

    @Test (expected = RuntimeException.class)
    public void nullOperationHandler() {
        fruitShopService.updateStorageInfo(correctOperations, null);
    }
}
