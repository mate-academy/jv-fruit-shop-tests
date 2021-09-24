package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.activities.ActivityHandler;
import core.basesyntax.service.activities.BalanceHandler;
import core.basesyntax.service.activities.PurchaseHandler;
import core.basesyntax.service.activities.ReturnHandler;
import core.basesyntax.service.activities.SupplyHandler;
import core.basesyntax.service.activities.TypeOfActivities;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ActivitiesStrategyImplTest {
    private static ActivitiesStrategy strategy;
    private static Map<TypeOfActivities, ActivityHandler> typeOfActivitiesMap;
    private Object expected;

    @Before
    public void setUp() {
        typeOfActivitiesMap = new HashMap<>();
        typeOfActivitiesMap.put(TypeOfActivities.BALANCE, new BalanceHandler());
        typeOfActivitiesMap.put(TypeOfActivities.SUPPLY, new SupplyHandler());
        typeOfActivitiesMap.put(TypeOfActivities.PURCHASE, new PurchaseHandler());
        typeOfActivitiesMap.put(TypeOfActivities.RETURN, new ReturnHandler());
        strategy = new ActivitiesStrategyImpl(typeOfActivitiesMap);
    }

    @Test
    public void activitiesStrategy_normalData_Ok() {
        assertEquals(strategy.get(TypeOfActivities.BALANCE).getClass(),BalanceHandler.class);
        assertEquals(strategy.get(TypeOfActivities.PURCHASE).getClass(),PurchaseHandler.class);
        assertEquals(strategy.get(TypeOfActivities.RETURN).getClass(),ReturnHandler.class);
        assertEquals(strategy.get(TypeOfActivities.SUPPLY).getClass(),SupplyHandler.class);
    }

    @Test(expected = RuntimeException.class)
    public void activitiesStrategy_incorrectData_notOk() {
        typeOfActivitiesMap.clear();
        typeOfActivitiesMap.put(TypeOfActivities.BALANCE, new BalanceHandler());
        typeOfActivitiesMap.put(TypeOfActivities.SUPPLY, new SupplyHandler());
        strategy.get(TypeOfActivities.RETURN);
    }
}
