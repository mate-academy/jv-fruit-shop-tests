package core.basesyntax.services;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitAvailabilityException;
import core.basesyntax.model.Activity;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.services.impl.ActivityValidatorImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityValidatorTest {
    private static ActivityValidator activityValidator;

    @BeforeClass
    public static void init() {
        activityValidator = new ActivityValidatorImpl();
    }

    @Before
    public void setUp() {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void validate_checkWithProperValues_OK() {
        Activity activity = new Activity.Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.BANANA)
                .setQuantity(100)
                .build();
        assertTrue(activityValidator.validate(activity));
    }

    @Test(expected = FruitAvailabilityException.class)
    public void validate_checkSupplyWithAbsentFruitInStorage_NotOK() {
        Activity activity = new Activity.Builder()
                .setActivityType(ActivityType.SUPPLY)
                .setFruit(Fruit.BANANA)
                .setQuantity(100)
                .build();
        assertTrue(activityValidator.validate(activity));
    }

    @Test(expected = FruitAvailabilityException.class)
    public void validate_checkPurchaseWithAbsentFruitInStorage_NotOK() {
        Activity activity = new Activity.Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.BANANA)
                .setQuantity(100)
                .build();
        assertTrue(activityValidator.validate(activity));
    }

    @Test(expected = FruitAvailabilityException.class)
    public void validate_checkReturnWithAbsentFruitInStorage_NotOK() {
        Activity activity = new Activity.Builder()
                .setActivityType(ActivityType.RETURN)
                .setFruit(Fruit.BANANA)
                .setQuantity(100)
                .build();
        assertTrue(activityValidator.validate(activity));
    }

    @Test(expected = FruitAvailabilityException.class)
    public void validate_checkPurchaseWithNotEnoughQuantityInFruitInStorage_NotOK() {
        Storage.fruitsStorage.put(Fruit.APPLE, 90);
        Activity activity = new Activity.Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.APPLE)
                .setQuantity(100)
                .build();
        assertTrue(activityValidator.validate(activity));
    }
}
