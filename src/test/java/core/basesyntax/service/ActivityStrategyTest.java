package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.activity.Activity;
import core.basesyntax.service.activity.BalanceActivity;
import core.basesyntax.service.activity.PurchaseActivity;
import core.basesyntax.service.activity.ReturnActivity;
import core.basesyntax.service.activity.SupplyActivity;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityStrategyTest {
    private static ActivityStrategy activityStrategy;

    @BeforeClass
    public static void setUp() throws Exception {
        Map<Fruit.Type, Activity> typeActivityMap = new HashMap<>();
        typeActivityMap.put(Fruit.Type.BALANCE, new BalanceActivity());
        typeActivityMap.put(Fruit.Type.PURCHASE, new PurchaseActivity());
        typeActivityMap.put(Fruit.Type.RETURN, new ReturnActivity());
        typeActivityMap.put(Fruit.Type.SUPPLY, new SupplyActivity());

        activityStrategy = new ActivityStrategyImpl(typeActivityMap);
    }

    @Test
    public void testActivityStrategy_Ok() {
        Class actual = activityStrategy.get(Fruit.Type.BALANCE).getClass();
        Class expected = new BalanceActivity().getClass();
        assertEquals(expected, actual);

        actual = activityStrategy.get(Fruit.Type.PURCHASE).getClass();
        expected = new PurchaseActivity().getClass();
        assertEquals(expected, actual);

        actual = activityStrategy.get(Fruit.Type.RETURN).getClass();
        expected = new ReturnActivity().getClass();
        assertEquals(expected, actual);

        actual = activityStrategy.get(Fruit.Type.SUPPLY).getClass();
        expected = new SupplyActivity().getClass();
        assertEquals(expected, actual);
    }
}
