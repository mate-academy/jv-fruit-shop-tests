package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActionTest {
    @Test
    public void getActionCode_checkBalanceAction_returnsTrue() {
        assertEquals("b", Action.BALANCE.getActionCode());
    }

    @Test
    public void getActionCode_checkSupplyAction_returnsTrue() {
        assertEquals("s", Action.SUPPLY.getActionCode());
    }

    @Test
    public void getActionCode_checkPurchaseAction_returnsTrue() {
        assertEquals("p", Action.PURCHASE.getActionCode());
    }

    @Test
    public void getActionCode_checkReturnAction_returnsTrue() {
        assertEquals("r", Action.RETURN.getActionCode());
    }
}
