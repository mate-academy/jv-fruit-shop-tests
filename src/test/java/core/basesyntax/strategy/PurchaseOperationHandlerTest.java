package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.PurchaseOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final Integer EXPECTED_RESULT = 10;
    private static final Integer SMALLER_VALUE = 10;
    private static final Integer BIGGER_VALUE = 20;
    private static final Integer NEGATIVE_TRANSACTION_VALUE = -15;
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchase_ok() {
        assertEquals(EXPECTED_RESULT,
                purchaseOperationHandler.operate(SMALLER_VALUE, BIGGER_VALUE));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseNotEnoughFruits_notOk() {
        purchaseOperationHandler.operate(BIGGER_VALUE, SMALLER_VALUE);
    }

    @Test(expected = RuntimeException.class)

    public void purchaseNegativeTransactionValue_notOk() {
        purchaseOperationHandler.operate(NEGATIVE_TRANSACTION_VALUE, BIGGER_VALUE);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseNullTransactionValue_notOK() {
        purchaseOperationHandler.operate(null, null);
    }
}
