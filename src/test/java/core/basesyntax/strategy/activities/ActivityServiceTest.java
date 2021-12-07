package core.basesyntax.strategy.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Activity;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityServiceTest {
    private static ActivityService balanceService;
    private static ActivityService purchaseService;
    private static ActivityService supplyService;
    private static ActivityService returnService;
    private static Activity balanceBananas20;
    private static Activity balanceApple100;
    private static Activity supplyBananas100;
    private static Activity purchaseBananas13;
    private static Activity returnApples10;
    private static Activity purchaseApples20;
    private static Activity purchaseBananas5;
    private static Activity supplyBananas50;
    private static int actualApplesQuantity;
    private static int expectedApplesQuantity;
    private static int expectedBananasQuantity;
    private static int actualBananasQuantity;

    @BeforeClass
    public static void setup() {
        balanceService = new BalanceService();
        purchaseService = new PurchaseService();
        returnService = new ReturnService();
        supplyService = new SupplyService();
        balanceBananas20 = new Activity.Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.BANANA)
                .setQuantity(20)
                .build();
        balanceApple100 = new Activity.Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.APPLE)
                .setQuantity(100)
                .build();
        supplyBananas100 = new Activity.Builder()
                .setActivityType(ActivityType.SUPPLY)
                .setFruit(Fruit.BANANA)
                .setQuantity(100)
                .build();
        purchaseBananas13 = new Activity.Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.BANANA)
                .setQuantity(13)
                .build();
        returnApples10 = new Activity.Builder()
                .setActivityType(ActivityType.RETURN)
                .setFruit(Fruit.APPLE)
                .setQuantity(10)
                .build();
        purchaseApples20 = new Activity.Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.APPLE)
                .setQuantity(20)
                .build();
        purchaseBananas5 = new Activity.Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.BANANA)
                .setQuantity(5)
                .build();
        supplyBananas50 = new Activity.Builder()
                .setActivityType(ActivityType.SUPPLY)
                .setFruit(Fruit.BANANA)
                .setQuantity(50)
                .build();
    }

    @Test
    public void apply_checkResultsWithApplyAllServices_OK() {
        balanceService.apply(balanceBananas20);
        balanceService.apply(balanceApple100);
        expectedApplesQuantity = balanceApple100.getQuantity();
        actualApplesQuantity = Storage.fruitsStorage.get(Fruit.APPLE);
        assertEquals(expectedApplesQuantity, actualApplesQuantity);
        expectedApplesQuantity = Storage.fruitsStorage.get(Fruit.APPLE)
                - purchaseApples20.getQuantity();
        purchaseService.apply(purchaseApples20);
        actualApplesQuantity = Storage.fruitsStorage.get(Fruit.APPLE);
        assertEquals(expectedApplesQuantity, actualApplesQuantity);
        expectedApplesQuantity = Storage.fruitsStorage.get(Fruit.APPLE)
                + returnApples10.getQuantity();
        returnService.apply(returnApples10);
        actualApplesQuantity = Storage.fruitsStorage.get(Fruit.APPLE);
        assertEquals(expectedApplesQuantity, actualApplesQuantity);
        expectedBananasQuantity = Storage.fruitsStorage.get(Fruit.BANANA)
                + supplyBananas50.getQuantity();
        supplyService.apply(supplyBananas50);
        actualBananasQuantity = Storage.fruitsStorage.get(Fruit.BANANA);
        assertEquals(expectedBananasQuantity, actualBananasQuantity);
    }

    @Test(expected = RuntimeException.class)
    public void apply_checkBalanceServiceWithNull_NotOK() {
        balanceService.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_checkPurchaseServiceWithNull_NotOK() {
        purchaseService.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_checkReturnServiceWithNull_NotOK() {
        returnService.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_checkSupplyServiceWithNull_NotOK() {
        supplyService.apply(null);
    }
}
