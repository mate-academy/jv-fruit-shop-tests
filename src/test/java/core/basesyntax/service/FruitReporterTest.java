package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Activity;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.FruitReporterImpl;
import core.basesyntax.strategy.activities.ActivityService;
import core.basesyntax.strategy.activities.BalanceService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReporterTest {
    private static FruitReporter fruitReporter;
    private static ActivityService activityService;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @BeforeClass
    public static void setup() {
        fruitReporter = new FruitReporterImpl();
    }

    @Test
    public void report_withValuesInCorrectOrder_OK() {
        activityService = new BalanceService();
        activityService.apply(new Activity.Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.APPLE)
                .setQuantity(100)
                .build());
        activityService.apply(new Activity.Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.BANANA)
                .setQuantity(20)
                .build());
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,100" + LINE_SEPARATOR
                + "banana,20" + LINE_SEPARATOR;
        String actual = fruitReporter.report(Storage.fruitsStorage);
        assertEquals(expected, actual);
    }

    @Test
    public void report_withValuesInWrongOrder_OK() {
        activityService = new BalanceService();
        activityService.apply(new Activity.Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.BANANA)
                .setQuantity(20)
                .build());
        activityService.apply(new Activity.Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.APPLE)
                .setQuantity(100)
                .build());
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,100" + LINE_SEPARATOR
                + "banana,20" + LINE_SEPARATOR;
        String actual = fruitReporter.report(Storage.fruitsStorage);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void report_withNull_OK() {
        fruitReporter.report(null);
    }
}
