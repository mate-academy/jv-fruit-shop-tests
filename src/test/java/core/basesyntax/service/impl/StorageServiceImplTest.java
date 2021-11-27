package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.StorageService;
import core.basesyntax.strategy.ActivityService;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.impl.BalanceService;
import core.basesyntax.strategy.impl.PurchaseService;
import core.basesyntax.strategy.impl.ReturnService;
import core.basesyntax.strategy.impl.SupplyService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StorageServiceImplTest {

    private static StorageService storageService;

    @Before
    public void beforeEachTest() {

        Map<String, ActivityService> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put("b", new BalanceService());
        activityHandlerMap.put("s", new SupplyService());
        activityHandlerMap.put("p", new PurchaseService());
        activityHandlerMap.put("r", new ReturnService());

        ActivityStrategy activityStrategy = new ActivityStrategy(activityHandlerMap);
        FruitService fruitService = new FruitServiceImpl();
        storageService = new StorageServiceImpl(activityStrategy, fruitService);

        Fruit apple = new Fruit("apple");
        Storage.getStore().clear();
        Storage.getStore().put(apple, 5);
    }

    @Test
    public void testGetEntry_ok() {
        Assert.assertEquals(5, storageService.getEntry("apple").getValue());
    }

    @Test
    public void testGetEntry_null() {
        Assert.assertEquals(null, storageService.getEntry("banana"));
    }

    @Test
    public void testReleaseActivity() {
        String activitySuppl = "s,apple,2";
        storageService.releaseActivity(activitySuppl);
        Assert.assertEquals(7, storageService.getEntry("apple").getValue());

        String activityBalance = "b,banana,3";
        storageService.releaseActivity(activityBalance);
        Assert.assertEquals(3, storageService.getEntry("banana").getValue());
    }

}
