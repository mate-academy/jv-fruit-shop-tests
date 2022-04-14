package core.basesyntax.activity;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.handler.FruitHandler;
import core.basesyntax.service.handler.RemoveFruitHandlerImpl;
import core.basesyntax.service.handler.UpdateFruitHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityStrategyImplTest {
    private static final FruitHandler ADD_TO_STORAGE = new UpdateFruitHandlerImpl();
    private static final FruitHandler TAKE_FROM_STORAGE = new RemoveFruitHandlerImpl();
    private static final Map<Activities, FruitHandler> handlerMap = new HashMap<>();
    private final ActivityStrategy activityStrategy = new ActivityStrategyImpl(handlerMap);

    @BeforeClass
    public static void setUp() {
        handlerMap.put(Activities.BALANCE, ADD_TO_STORAGE);
        handlerMap.put(Activities.PURCHASE, TAKE_FROM_STORAGE);
        handlerMap.put(Activities.RETURN, ADD_TO_STORAGE);
        handlerMap.put(Activities.SUPPLY, ADD_TO_STORAGE);
    }

    @Test
    public void get_Balance_ok() {
        FruitHandler actual = activityStrategy.get(Activities.BALANCE);
        assertEquals(ADD_TO_STORAGE, actual);
    }

    @Test
    public void get_Purchase_ok() {
        FruitHandler actual = activityStrategy.get(Activities.PURCHASE);
        assertEquals(TAKE_FROM_STORAGE, actual);
    }

    @Test
    public void get_return_ok() {
        FruitHandler actual = activityStrategy.get(Activities.RETURN);
        assertEquals(ADD_TO_STORAGE, actual);
    }

    @Test
    public void get_supply_ok() {
        FruitHandler actual = activityStrategy.get(Activities.SUPPLY);
        assertEquals(ADD_TO_STORAGE, actual);
    }
}
