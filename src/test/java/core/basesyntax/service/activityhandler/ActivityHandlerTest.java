package core.basesyntax.service.activityhandler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActivityHandlerTest {
    private static final int PLUS_AMOUNT = 100;
    private static final int MINUS_AMOUNT = -500;
    private final BalanceActivityHandler balanceActivityHandler = new BalanceActivityHandler();
    private final PurchaseActivityHandler purchaseActivityHandler = new PurchaseActivityHandler();
    private final ReturnActivityHandler returnActivityHandler = new ReturnActivityHandler();
    private final SupplyActivityHandler supplyActivityHandler = new SupplyActivityHandler();

    @Test
    public void balanceHandlerPositive_OK() {
        assertEquals(PLUS_AMOUNT, balanceActivityHandler.get(PLUS_AMOUNT));
    }

    @Test (expected = IllegalArgumentException.class)
    public void balanceHandlerNegative_OK() {
        balanceActivityHandler.get(MINUS_AMOUNT);
    }

    @Test (expected = IllegalArgumentException.class)
    public void purchaseHandlerPositive_OK() {
        purchaseActivityHandler.get(PLUS_AMOUNT);
    }

    @Test
    public void purchaseHandlerNegative_OK() {
        assertEquals(MINUS_AMOUNT * -1, purchaseActivityHandler.get(MINUS_AMOUNT));
    }

    @Test
    public void returnHandlerPositive_OK() {
        assertEquals(PLUS_AMOUNT, returnActivityHandler.get(PLUS_AMOUNT));
    }

    @Test (expected = IllegalArgumentException.class)
    public void returnHandlerNegative_OK() {
        returnActivityHandler.get(MINUS_AMOUNT);
    }

    @Test
    public void supplyHandlerPositive_OK() {
        assertEquals(PLUS_AMOUNT, supplyActivityHandler.get(PLUS_AMOUNT));
    }

    @Test (expected = IllegalArgumentException.class)
    public void supplyHandlerNegative_OK() {
        supplyActivityHandler.get(MINUS_AMOUNT);
    }
}
