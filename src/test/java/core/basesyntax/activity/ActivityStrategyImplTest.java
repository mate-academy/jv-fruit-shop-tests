package core.basesyntax.activity;

import static org.junit.Assert.assertEquals;

import core.basesyntax.storage.dao.AddToStorageHandlerImpl;
import core.basesyntax.storage.dao.StorageHandler;
import core.basesyntax.storage.dao.TakeFromStorageHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityStrategyImplTest {
    private static final StorageHandler ADD_TO_STORAGE = new AddToStorageHandlerImpl();
    private static final StorageHandler TAKE_FROM_STORAGE = new TakeFromStorageHandlerImpl();
    private static Map<Activities, StorageHandler> handlerMap = new HashMap<>();
    private final ActivityStrategy activityStrategy = new ActivityStrategyImpl(handlerMap);

    @BeforeClass
    public static void fillMap() {
        handlerMap.put(Activities.BALANCE, ADD_TO_STORAGE);
        handlerMap.put(Activities.PURCHASE, TAKE_FROM_STORAGE);
        handlerMap.put(Activities.RETURN, ADD_TO_STORAGE);
        handlerMap.put(Activities.SUPPLY, ADD_TO_STORAGE);
    }

    @Test
    public void get_Balance_ok() {
        StorageHandler actual = activityStrategy.get(Activities.BALANCE);
        assertEquals(ADD_TO_STORAGE, actual);
    }

    @Test
    public void get_Purchase_ok() {
        StorageHandler actual = activityStrategy.get(Activities.PURCHASE);
        assertEquals(TAKE_FROM_STORAGE, actual);
    }

    @Test
    public void get_return_ok() {
        StorageHandler actual = activityStrategy.get(Activities.RETURN);
        assertEquals(ADD_TO_STORAGE, actual);
    }

    @Test
    public void get_supply_ok() {
        StorageHandler actual = activityStrategy.get(Activities.SUPPLY);
        assertEquals(ADD_TO_STORAGE, actual);
    }
}
