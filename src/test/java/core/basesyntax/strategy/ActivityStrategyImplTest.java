package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.report.FruitBalance;
import core.basesyntax.strategy.activity.ActivityHandler;
import core.basesyntax.strategy.activity.ActivityHandlerAddImpl;
import core.basesyntax.strategy.activity.ActivityHandlerSubstractionImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityStrategyImplTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static Map<String, ActivityHandler> activityHandlerMap;
    private static ActivityHandler activityHandler;
    private static ActivityStrategy activityStrategy;

    @BeforeAll
    static void beforeAll() {
        activityHandlerMap = new HashMap<>();
        activityHandler = new ActivityHandlerAddImpl();
        activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
    }

    @BeforeEach
    void setUp() {
        activityHandlerMap.put(BALANCE, new ActivityHandlerAddImpl());
        activityHandlerMap.put(SUPPLY, new ActivityHandlerAddImpl());
        activityHandlerMap.put(PURCHASE, new ActivityHandlerSubstractionImpl());
        activityHandlerMap.put(RETURN, new ActivityHandlerAddImpl());
    }

    @Test
    void activityStrategyBalanceOk() {
        assertEquals(activityStrategy.get(BALANCE), activityHandlerMap.get(BALANCE));
    }

    @Test
    void activityStrategySupplyOk() {
        assertEquals(activityStrategy.get(SUPPLY), activityHandlerMap.get(SUPPLY));
    }

    @Test
    void activityStrategyReturnNotOk() {
        assertNotEquals(activityStrategy.get(RETURN), activityHandlerMap.get(PURCHASE));
    }

    @AfterEach
    void tearDown() {
        activityHandlerMap.clear();
        FruitBalance.FRUIT_BALANCE.clear();
        Storage.records.clear();
    }
}
