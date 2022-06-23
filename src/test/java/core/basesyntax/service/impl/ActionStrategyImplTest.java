package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.dao.ActionsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.actiontype.ActionStrategyBalance;
import core.basesyntax.service.actiontype.ActionStrategyPurchase;
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
    private static ActionsDao actionsDao;

    @BeforeClass
    public static void beforeClass() {
        actionsDao = new ActionsDaoImpl(Storage.data);
        mapStrategy.put("b", new ActionStrategyBalance(actionsDao));
        mapStrategy.put("p", new ActionStrategyPurchase(actionsDao));
        mapStrategy.put("s", new ActionStrategySupplier(actionsDao));
        mapStrategy.put("r", new ActionStrategyReturner(actionsDao));
        actionStrategy = new ActionStrategyImpl(mapStrategy);
    }

    @Test
    public void getStrategy_inputWrongStrategy_notOk() {
        ActionType actionType = actionStrategy.get("w");
        assertEquals(null, actionType);
    }

    @Test
    public void getStrategy_inputValidStrategyPurchase_ok() {
        ActionType actionType = actionStrategy.get("p");
        assertEquals(ActionStrategyPurchase.class, actionType.getClass());
    }
}
