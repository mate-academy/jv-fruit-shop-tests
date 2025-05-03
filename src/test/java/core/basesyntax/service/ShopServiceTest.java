package core.basesyntax.service;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.activity.ActivityHandler;
import core.basesyntax.service.activity.ActivityType;
import core.basesyntax.service.activity.AddingHandler;
import core.basesyntax.service.activity.RemovingHandler;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.ActivityStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ShopServiceTest {
    private static ShopService shopService;
    private static Map<String, ActivityHandler> activityHandlerMap;
    private static List<String> inputData;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        FruitStorageDao fruitDao = new FruitStorageDaoImpl();
        activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(ActivityType.BALANCE.getName(), new AddingHandler(fruitDao));
        activityHandlerMap.put(ActivityType.PURCHASE.getName(), new RemovingHandler(fruitDao));
        activityHandlerMap.put(ActivityType.SUPPLY.getName(), new AddingHandler(fruitDao));
        activityHandlerMap.put(ActivityType.RETURN.getName(), new AddingHandler(fruitDao));
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
        shopService = new ShopServiceImpl(activityStrategy, fruitDao);
    }

    @Before
    public void clearResults() {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void updateShopWarehouse_validData_ok() {
        inputData = List.of("type, fruit, quantity",
                "b,banana,2",
                "b,apple,3",
                "s,banana,2",
                "p,banana,3",
                "r,apple,3",
                "p,apple,1",
                "p,banana,1",
                "s,banana,2");
        List<Fruit> expected =
                List.of(new Fruit("apple"),
                        new Fruit("apple"),
                        new Fruit("apple"),
                        new Fruit("apple"),
                        new Fruit("apple"),
                        new Fruit("banana"),
                        new Fruit("banana"));
        List<Fruit> actual = shopService.updateShopWarehouse(inputData);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateShopWarehouse_invalidData_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not enough banana to purchase: 5");
        inputData = List.of("type, fruit, quantity",
                "b,banana,2",
                "b,apple,3",
                "s,banana,2",
                "p,banana,3",
                "r,apple,3",
                "p,apple,1",
                "p,banana,5",
                "s,banana,2");
        shopService.updateShopWarehouse(inputData);
    }

    @Test
    public void updateShopWarehouse_invalidFirstTransactionData_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not enough banana to purchase: 20");
        inputData = List.of("type, fruit, quantity",
                "p,banana,20",
                "b,apple,3");
        shopService.updateShopWarehouse(inputData);
    }

    @Test(expected = NullPointerException.class)
    public void updateShopWarehouse_nullData_notOk() {
        shopService.updateShopWarehouse(null);
    }
}
