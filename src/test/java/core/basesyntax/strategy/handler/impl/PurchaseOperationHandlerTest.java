package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {

    private static final Integer SMALL_VALUE = 10;
    private static final Integer BIG_VALUE = 25;
    private static final Integer EXPECTED_VALUE = 15;
    private static OperationHandler purchaseHandler;

    @BeforeClass
    public static void beforeAll() {
        purchaseHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchase_ok() {
        assertEquals(EXPECTED_VALUE,purchaseHandler.operate(SMALL_VALUE, BIG_VALUE));
    }

    @Test (expected = RuntimeException.class)
    public void purchase_notOK() {
        purchaseHandler.operate(BIG_VALUE, SMALL_VALUE);
    }

}
