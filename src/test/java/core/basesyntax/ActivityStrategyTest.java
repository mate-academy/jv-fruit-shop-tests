package core.basesyntax;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.models.activities.ActivityHandler;
import core.basesyntax.models.activities.BalanceActivityHandler;
import core.basesyntax.models.activities.PurchaseActivityHandler;
import core.basesyntax.models.activities.ReturnActivityHandler;
import core.basesyntax.models.activities.SupplyActivityHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ActivityStrategyTest {

    private final Map<FruitTransaction.TypeOfActivity, ActivityHandler> activityHandlerMap = Map.of(
            FruitTransaction.TypeOfActivity.BALANCE, new BalanceActivityHandler(),
            FruitTransaction.TypeOfActivity.SUPPLY, new SupplyActivityHandler(),
            FruitTransaction.TypeOfActivity.PURCHASE, new PurchaseActivityHandler(),
            FruitTransaction.TypeOfActivity.RETURN, new ReturnActivityHandler()
    );

    @Test
    public void testActivityStrategy_Ok() {
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
        activityHandlerMap.forEach((type, handler) ->
                Assert.assertEquals(handler, activityStrategy.getActivity(type)));
    }

    @Test
    public void testNullOrEmptyActivityStrategy_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> new ActivityStrategyImpl(new HashMap<>()));
        Assert.assertThrows(RuntimeException.class,
                () -> new ActivityStrategyImpl(null));
    }

}
