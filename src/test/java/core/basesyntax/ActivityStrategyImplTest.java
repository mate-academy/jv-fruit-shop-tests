package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.ActivityHandler;
import core.basesyntax.service.strategy.ActivityStrategyImpl;
import core.basesyntax.service.strategy.BalanceActivityHandler;
import core.basesyntax.service.strategy.PurchaseActivityHandler;
import core.basesyntax.service.strategy.ReturnActivityHandler;
import core.basesyntax.service.strategy.SupplyActivityHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityStrategyImplTest {
    private ActivityStrategyImpl activityStrategy;

    @BeforeEach
    public void setUp() {
        activityStrategy = new ActivityStrategyImpl(getActivitiesServiceMap());
    }

    @Test
    public void testGetQuantityModifier_Success() {
        ActivityHandler balanceHandler = activityStrategy
                .getQuantityModifier(Fruit.Operation.BALANCE);
        assertTrue(balanceHandler instanceof BalanceActivityHandler);
        ActivityHandler purchaseHandler = activityStrategy
                .getQuantityModifier(Fruit.Operation.PURCHASE);
        assertTrue(purchaseHandler instanceof PurchaseActivityHandler);
        ActivityHandler supplyHandler = activityStrategy
                .getQuantityModifier(Fruit.Operation.SUPPLY);
        assertTrue(supplyHandler instanceof SupplyActivityHandler);
        ActivityHandler returnHandler = activityStrategy
                .getQuantityModifier(Fruit.Operation.RETURN);
        assertTrue(returnHandler instanceof ReturnActivityHandler);
    }

    private Map<Fruit.Operation, ActivityHandler> getActivitiesServiceMap() {
        Map<Fruit.Operation, ActivityHandler> activitiesServiceMap = new HashMap<>();
        activitiesServiceMap.put(Fruit.Operation.BALANCE, new BalanceActivityHandler());
        activitiesServiceMap.put(Fruit.Operation.PURCHASE, new PurchaseActivityHandler());
        activitiesServiceMap.put(Fruit.Operation.SUPPLY, new SupplyActivityHandler());
        activitiesServiceMap.put(Fruit.Operation.RETURN, new ReturnActivityHandler());
        return activitiesServiceMap;
    }
}
