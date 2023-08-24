package db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Activity;
import model.ActivityType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.ActivityTypeHandler;
import strategy.ActivityTypeStrategy;
import strategy.impl.ActivityTypeStrategyImpl;
import strategy.impl.AdditionTypeHandler;
import strategy.impl.BalanceTypeHandler;
import strategy.impl.PurchaseTypeHandler;

public class StorageFillerImplTest {
    private static final StorageFiller storageFiller = new StorageFillerImpl();
    private static final String FRUIT_NAME_ONE = "fruit1";
    private static final String FRUIT_NAME_TWO = "fruit2";
    private static final String FRUIT_NAME_THREE = "fruit3";
    private static final String FRUIT_NAME_FORE = "fruit4";
    private static final int DEFAULT_QUANTITY = 100;
    private static ActivityTypeStrategy activityTypeStrategy;
    private static Storage storage;
    private static List<Activity> activityList;

    @BeforeAll
    static void beforeAll() {
        Map<ActivityType, ActivityTypeHandler> activityTypeHandlerMap = new HashMap<>();
        activityTypeHandlerMap.put(ActivityType.BALANCE, new BalanceTypeHandler());
        activityTypeHandlerMap.put(ActivityType.SUPPLY, new AdditionTypeHandler());
        activityTypeHandlerMap.put(ActivityType.RETURN, new AdditionTypeHandler());
        activityTypeHandlerMap.put(ActivityType.PURCHASE, new PurchaseTypeHandler());

        activityTypeStrategy = new ActivityTypeStrategyImpl(
                activityTypeHandlerMap);
    }

    @BeforeEach
    public void beforeEach() {
        activityList = new ArrayList<>();
        storage = new Storage(new HashMap<>());
    }

    @Test
    public void emptyActivityList_Ok() {
        storageFiller.fullfillStorage(storage, activityList, activityTypeStrategy);
        assertTrue(storage.getFruitBox().isEmpty());
    }

    @Test
    public void defaultActivityList_Ok() {
        activityList.add(new Activity(ActivityType.BALANCE, FRUIT_NAME_ONE, DEFAULT_QUANTITY));
        activityList.add(new Activity(ActivityType.SUPPLY, FRUIT_NAME_TWO, DEFAULT_QUANTITY));
        activityList.add(new Activity(ActivityType.PURCHASE, FRUIT_NAME_THREE, DEFAULT_QUANTITY));
        activityList.add(new Activity(ActivityType.RETURN, FRUIT_NAME_FORE, DEFAULT_QUANTITY));

        storageFiller.fullfillStorage(storage, activityList, activityTypeStrategy);
        var fruitBox = storage.getFruitBox();
        assertEquals(4, fruitBox.size(), "Expected fruitBox size is 4");
    }

    @Test
    public void nullTypeActivity_NotOk() {
        assertThrows(NullPointerException.class, () ->
                activityList.add(new Activity(null, FRUIT_NAME_ONE, DEFAULT_QUANTITY)));
    }

    @Test
    public void nullFruitActivity_NotOk() {
        assertThrows(NullPointerException.class, () ->
                activityList.add(new Activity(ActivityType.BALANCE, null, DEFAULT_QUANTITY)));
    }
}

