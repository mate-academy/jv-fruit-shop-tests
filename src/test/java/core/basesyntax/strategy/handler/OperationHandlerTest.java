package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerTest {

    private static final int DEFAULT_QUANTITY = 0;
    private static final int DEFAULT_VALUE = 100;
    private static OperationHandler operationHandlerBalance;
    private static OperationHandler operationHandlerPurchase;
    private static OperationHandler operationHandlerReturn;
    private static OperationHandler operationHandlerSupply;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerBalance = new BalanceHandler();
        operationHandlerPurchase = new PurchaseHandler();
        operationHandlerReturn = new ReturnHandler();
        operationHandlerSupply = new SupplyHandler();
    }

    @Test
    public void getOperationHandler_getValidBehaviour_ok() {
        Integer expected = DEFAULT_VALUE;
        Integer actual = operationHandlerBalance
                .getOperationHandler(DEFAULT_QUANTITY, DEFAULT_VALUE);
        assertEquals(expected, actual);
        expected = -DEFAULT_VALUE;
        actual = operationHandlerPurchase
                .getOperationHandler(DEFAULT_QUANTITY, DEFAULT_VALUE);
        assertEquals(expected, actual);
        expected = DEFAULT_VALUE;
        actual = operationHandlerReturn
                .getOperationHandler(DEFAULT_QUANTITY, DEFAULT_VALUE);
        assertEquals(expected, actual);
        expected = DEFAULT_VALUE;
        actual = operationHandlerSupply
                .getOperationHandler(DEFAULT_QUANTITY, DEFAULT_VALUE);
        assertEquals(expected, actual);
    }
}
