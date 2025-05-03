package core.basesyntax.strategy.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Activity;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityServiceTest {
    private static ActivityService balanceService;
    private static ActivityService purchaseService;
    private static ActivityService supplyService;
    private static ActivityService returnService;

    @BeforeClass
    public static void setup() {
        balanceService = new BalanceService();
        purchaseService = new PurchaseService();
        returnService = new ReturnService();
        supplyService = new SupplyService();
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void apply_checkResultsWithApplyBalanceService_OK() {
        int expectedQuantity = 100;
        balanceService.apply(new Activity
                .Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.APPLE)
                .setQuantity(expectedQuantity)
                .build());
        int actualQuantity = Storage.fruitsStorage.get(Fruit.APPLE);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void apply_checkResultsWithApplyPurchaseService_OK() {
        int expectedQuantity = 100;
        Storage.fruitsStorage.put(Fruit.APPLE, 200);
        purchaseService.apply(new Activity
                .Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.APPLE)
                .setQuantity(100)
                .build());
        int actualQuantity = Storage.fruitsStorage.get(Fruit.APPLE);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void apply_checkResultsWithApplyReturnService_OK() {
        int expectedQuantity = 200;
        Storage.fruitsStorage.put(Fruit.BANANA, 100);
        returnService.apply(new Activity
                .Builder()
                .setActivityType(ActivityType.RETURN)
                .setFruit(Fruit.BANANA)
                .setQuantity(100)
                .build());
        int actualQuantity = Storage.fruitsStorage.get(Fruit.BANANA);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void apply_checkResultsWithApplySupplyService_OK() {
        int expectedQuantity = 200;
        Storage.fruitsStorage.put(Fruit.BANANA, 100);
        supplyService.apply(new Activity
                .Builder()
                .setActivityType(ActivityType.SUPPLY)
                .setFruit(Fruit.BANANA)
                .setQuantity(100)
                .build());
        int actualQuantity = Storage.fruitsStorage.get(Fruit.BANANA);
        assertEquals(expectedQuantity, actualQuantity);
    }
}
