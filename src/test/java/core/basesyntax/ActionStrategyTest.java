package core.basesyntax;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.ActionStrategyImpl;
import core.basesyntax.service.handler.ActionHandler;
import core.basesyntax.service.handler.BalanceActionHandler;
import core.basesyntax.service.handler.PurchaseActionHandler;
import core.basesyntax.service.handler.ReturnActionHandler;
import core.basesyntax.service.handler.SupplyActionHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActionStrategyTest {
    private static final String BALANCE_ACTION_KEY = "b";
    private static final String SUPPLY_ACTION_KEY = "s";
    private static final String PURCHASE_ACTION_KEY = "p";
    private static final String RETURN_ACTION_KEY = "r";
    private static final String INVALID_ACTION_KEY = "i";
    private static ActionStrategy actionStrategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        actionStrategy = new ActionStrategyImpl(getActionHandlerMap());
    }

    @Test
    public void get_validKeyBalanceActionHandler_returnsTrue() {
        Optional<ActionHandler> balanceActionHandlerOptional =
                actionStrategy.get(BALANCE_ACTION_KEY);
        assertTrue(balanceActionHandlerOptional.get().getClass()
                .equals(new BalanceActionHandler().getClass()));
    }

    @Test
    public void get_validKeySupplyActionHandler_returnsTrue() {
        Optional<ActionHandler> supplyActionHandlerOptional =
                actionStrategy.get(SUPPLY_ACTION_KEY);
        assertTrue(supplyActionHandlerOptional.get().getClass()
                .equals(new SupplyActionHandler().getClass()));
    }

    @Test
    public void get_validKeyReturnActionHandler_returnsTrue() {
        Optional<ActionHandler> returnActionHandlerOptional =
                actionStrategy.get(RETURN_ACTION_KEY);
        assertTrue(returnActionHandlerOptional.get().getClass()
                .equals(new ReturnActionHandler().getClass()));
    }

    @Test
    public void get_validKeyPurchaseActionHandler_returnsTrue() {
        Optional<ActionHandler> purchaseActionHandlerOptional =
                actionStrategy.get(PURCHASE_ACTION_KEY);
        assertTrue(purchaseActionHandlerOptional.get().getClass()
                .equals(new PurchaseActionHandler().getClass()));
    }

    @Test
    public void get_differentActionHandlers_returnsFalse() {
        Optional<ActionHandler> purchaseActionHandlerOptional =
                actionStrategy.get(PURCHASE_ACTION_KEY);
        Optional<ActionHandler> supplyActionHandlerOptional =
                actionStrategy.get(SUPPLY_ACTION_KEY);
        assertFalse(purchaseActionHandlerOptional.get().getClass()
                .equals(supplyActionHandlerOptional.get().getClass()));
    }

    @Test(expected = NoSuchElementException.class)
    public void test_invalidKeyActionHandler_NoSuchElementException() {
        Optional<ActionHandler> actionHandlerOptional =
                actionStrategy.get(INVALID_ACTION_KEY);
        actionHandlerOptional.get();
    }

    private static Map<String, ActionHandler> getActionHandlerMap() {
        Map<String, ActionHandler> actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(BALANCE_ACTION_KEY, new BalanceActionHandler());
        actionHandlerMap.put(SUPPLY_ACTION_KEY, new SupplyActionHandler());
        actionHandlerMap.put(PURCHASE_ACTION_KEY, new PurchaseActionHandler());
        actionHandlerMap.put(RETURN_ACTION_KEY, new ReturnActionHandler());
        return actionHandlerMap;
    }
}
