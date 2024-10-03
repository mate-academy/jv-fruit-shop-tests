package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Action;
import core.basesyntax.service.impl.PurchaseAction;
import core.basesyntax.service.impl.ReturnAction;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ActionStrategyTest {
    private static final ReturnAction returnAction = new ReturnAction();
    private static final ActionStrategy actionStrategy = new ActionStrategyImpl(
            Map.of(Action.PURCHASE, new PurchaseAction(), Action.RETURN, returnAction));

    @Test
    public void actionStrategy_getHandler_Ok() {
        assertEquals(actionStrategy.getHandler(Action.RETURN), returnAction);
    }
}
