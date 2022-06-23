package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.actiontype.ActionStrategyBalance;
import core.basesyntax.service.actiontype.ActionStrategyProducer;
import core.basesyntax.service.actiontype.ActionStrategyReturner;
import core.basesyntax.service.actiontype.ActionStrategySupplier;
import core.basesyntax.service.actiontype.ActionType;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActionStrategyImplTest {
    private static final Map<String, ActionType> mapStrategy = new HashMap<>();
    private static ActionStrategy actionStrategy;

    @BeforeClass
    public static void beforeClass() {
        mapStrategy.put("b", new ActionStrategyBalance());
        mapStrategy.put("p", new ActionStrategyProducer());
        mapStrategy.put("s", new ActionStrategySupplier());
        mapStrategy.put("r", new ActionStrategyReturner());
        actionStrategy = new ActionStrategyImpl(mapStrategy);
    }

    @Test
    public void inputWrongStrategy_NotOk() {
        ActionType actionType = actionStrategy.get("w");
        assertEquals(null, actionType);
    }

    @Test
    public void getValidStrategyProducer_Ok() {
        ActionType actionType = actionStrategy.get("p");
        assertEquals(ActionStrategyProducer.class, actionType.getClass());
    }
}
