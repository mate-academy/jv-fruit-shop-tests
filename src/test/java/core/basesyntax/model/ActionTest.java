package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ActionTest {
    private static final String SUPPLY_CODE = "s";
    private static final String BALANCE_CODE = "b";
    private static final String REMOVE_CODE = "r";
    private static final String PURCHASE_CODE = "p";
    private static final String INVALID_CODE = "wrong";
    private static final String NULL_CODE = null;

    @Test
    public void action_PurchaseCode_Ok() {
        assertEquals(Action.PURCHASE, Action.fromCode(PURCHASE_CODE));
    }

    @Test
    public void action_BalanceCode_Ok() {
        assertEquals(Action.BALANCE, Action.fromCode(BALANCE_CODE));
    }

    @Test
    public void action_RemoveCode_Ok() {
        assertEquals(Action.RETURN, Action.fromCode(REMOVE_CODE));
    }

    @Test
    public void action_SupplyCode_Ok() {
        assertEquals(Action.SUPPLY, Action.fromCode(SUPPLY_CODE));
    }

    @Test
    public void action_InvalidCode_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Action.fromCode(INVALID_CODE);
        });
    }

    @Test
    public void action_NullCode_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Action.fromCode(NULL_CODE);
        });
    }
}
