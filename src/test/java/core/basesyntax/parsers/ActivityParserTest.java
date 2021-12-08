package core.basesyntax.parsers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Activity;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.parsers.impl.ActivityParserImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityParserTest {
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

    @Test
    public void parse_getActivityWithActivityParserWithSpaces_OK() {
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
