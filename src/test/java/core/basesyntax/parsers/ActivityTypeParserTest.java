package core.basesyntax.parsers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.ActivityType;
import core.basesyntax.parsers.impl.ActivityTypeParserImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityTypeParserTest {
    private static ActivityTypeParser activityTypeParser;

    @BeforeClass
    public static void setup() {
        activityTypeParser = new ActivityTypeParserImpl();
    }

    @Test
    public void parse_getActivityTypeWithActivityTypeParser() {
        assertEquals(ActivityType.BALANCE, activityTypeParser.parse('b'));
        assertEquals(ActivityType.PURCHASE, activityTypeParser.parse('p'));
        assertEquals(ActivityType.RETURN, activityTypeParser.parse('r'));
        assertEquals(ActivityType.SUPPLY, activityTypeParser.parse('s'));
    }
}
