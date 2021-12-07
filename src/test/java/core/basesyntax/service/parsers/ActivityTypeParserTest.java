package core.basesyntax.service.parsers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Activity;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.parsers.impl.ActivityParserImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityTypeParserTest {
    private static ActivityParser activityParser;

    @BeforeClass
    public static void setup() {
        activityParser = new ActivityParserImpl();
    }

    @Test
    public void parse_getActivityWithActivityParser_OK() {
        String line = "b,banana,20";
        Activity expectedActivity = new Activity.Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.BANANA)
                .setQuantity(20).build();
        assertEquals(expectedActivity, activityParser.parse(line));
    }

    @Test(expected = RuntimeException.class)
    public void parse_getActivityWithActivityParserWithNull_NotOK() {
        activityParser.parse(null);
    }

    @Test
    public void parse_ActivityParserWithSpaces_OK() {
        Activity expected = new Activity
                .Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.BANANA)
                .setQuantity(20)
                .build();
        String line = "b, banana, 20";
        Activity actual = activityParser.parse(line);
        assertEquals(expected, actual);
    }
}
