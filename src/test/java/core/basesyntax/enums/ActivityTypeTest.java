package core.basesyntax.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ActivityTypeTest {
    private final Map<String, ActivityType> activities;

    public ActivityTypeTest() {
        activities = new HashMap<>();
        activities.put("b", ActivityType.BALANCE);
        activities.put("s", ActivityType.SUPPLY);
        activities.put("p", ActivityType.PURCHASE);
        activities.put("r", ActivityType.RETURN);
    }

    @Test
    void findByCode_Ok() {
        for (var activity : activities.entrySet()) {
            assertEquals(activity.getValue(), ActivityType.findByCode(activity.getKey()));
        }
    }

    @Test
    void getCode_Ok() {
        for (var activity : activities.entrySet()) {
            assertEquals(activity.getKey(), activity.getValue().getCode());
        }
    }
}
