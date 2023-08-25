package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import model.Activity;
import org.junit.Test;
import service.ActivityParserService;

public class ActivityParserServiceImplTest {
    private static final ActivityParserService parserService = new ActivityParserServiceImpl();
    private static final String[] TRANSACTIONS = {
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
    private static List<Activity> list;

    @Test
    public void emptyActivityString_Ok() {
        list = parserService.getActivityList(new String[] {});
        assertTrue(list.isEmpty());
    }

    @Test
    public void defaultActivityString_Ok() {
        list = parserService.getActivityList(TRANSACTIONS);
        assertEquals(9, list.size(), "Expected activity list size is 9");
    }

    @Test
    public void nullStringArray_NotOk() {
        assertThrows(NullPointerException.class, () ->
                parserService.getActivityList(null));
    }
}
