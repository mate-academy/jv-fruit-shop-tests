package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.InvalidParametersException;
import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperarionHandlerPurchaseTest {
    private static OperationHandler purchaseHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        purchaseHandler = new OperarionHandlerPurchase();
    }

    @Test
    public void changeSaldo_validValue() {
        assertEquals(purchaseHandler.changeSaldo(10).applyAsInt(5),5);
        assertEquals(purchaseHandler.changeSaldo(5).applyAsInt(0),5);
    }

    @Test(expected = InvalidParametersException.class)
    public void changeSaldo_invalidSaldo_notOk() {
        purchaseHandler.changeSaldo(-1).applyAsInt(10);
    }

    @Test(expected = InvalidParametersException.class)
    public void changeSaldo_invalidQuantity_notOk() {
        purchaseHandler.changeSaldo(10).applyAsInt(-1);
    }
}
