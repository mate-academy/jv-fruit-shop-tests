package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import model.Activity;
import org.junit.Test;
import service.ActivityParserService;

public class ActivityParserServiceImplTest {
    private static final ActivityParserService PARSER_SERVICE = new ActivityParserServiceImpl();
    private static final String[] EMPTY_STRING = {};
    private static final String[] DEFAULT_STRING = {
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50",
            "b,kiwi,10"
    };
    private static List<Activity> activityList;

    @Test
    public void emptyActivityString_Ok() {
        activityList = PARSER_SERVICE.getActivityList(EMPTY_STRING);
        assertTrue(activityList.isEmpty());
    }

    @Test
    public void defaultActivityString_Ok() {
        activityList = PARSER_SERVICE.getActivityList(DEFAULT_STRING);
        assertEquals(9, activityList.size(), "Expected activity list size is 9");
    }

    @Test
    public void nullStringArray_NotOk() {
        assertThrows(NullPointerException.class, () ->
                PARSER_SERVICE.getActivityList(null));
    }

}
