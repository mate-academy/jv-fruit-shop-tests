package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitMapDao;
import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.AddOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.RemoveOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceImplTest {
    private static ShopService shopService;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static List<String> inputData;
    private static Fruit apple;
    private static Fruit banana;

    @BeforeClass
    public static void beforeClass() {
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        FruitStorageDao fruitDao = new FruitMapDao();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE.getOperation(),
                new AddOperation(fruitDao));
        operationHandlerMap.put(OperationType.PURCHASE.getOperation(),
                new RemoveOperation(fruitDao));
        operationHandlerMap.put(OperationType.SUPPLY.getOperation(),
                new AddOperation(fruitDao));
        operationHandlerMap.put(OperationType.RETURN.getOperation(),
                new AddOperation(fruitDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Before
    public void setUp() {
        Storage.map.clear();
    }

    @Test
    public void updateShopWarehouse_validData_ok() {
        inputData = List.of(
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
        Map<Fruit, Integer> expected =
                Map.of(banana, 152,
                apple, 90);
        shopService.updateFruitsWarehouse(inputData);
        Map<Fruit, Integer> actual = Storage.map;
        System.out.println(actual.get(apple));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void updateShopWarehouse_invalidData_notOk() {
        inputData = List.of(
            "b,banana,1",
            "p,banana,2",
            "b,apple,1",
            "p,apple,2");
        shopService.updateFruitsWarehouse(inputData);
    }
}
