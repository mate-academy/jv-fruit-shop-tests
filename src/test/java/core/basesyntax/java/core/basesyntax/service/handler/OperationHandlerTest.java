package core.basesyntax.java.core.basesyntax.service.handler;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerTest {

    public static final int DEFAULT_QUANTITY = 0;
    public static final int DEFAULT_VALUE = 100;
    private static OperationHandler operationHandlerBalance;
    private static OperationHandler operationHandlerPurchase;
    private static OperationHandler operationHandlerReturn;
    private static OperationHandler operationHandlerSupply;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandlerBalance = new BalanceHandler();
        operationHandlerPurchase = new PurchaseHandler();
        operationHandlerReturn = new ReturnHandler();
        operationHandlerSupply = new SupplyHandler();
    }

    @Test
    public void getOperationHandler_ok() {
        Integer expected = 100;
        Integer actual = operationHandlerBalance
                .getOperationHandler(DEFAULT_QUANTITY, DEFAULT_VALUE);
        assertEquals(expected, actual);
        expected = -100;
        actual = operationHandlerPurchase
                .getOperationHandler(DEFAULT_QUANTITY, DEFAULT_VALUE);
        assertEquals(expected, actual);
        expected = 100;
        actual = operationHandlerReturn
                .getOperationHandler(DEFAULT_QUANTITY, DEFAULT_VALUE);
        assertEquals(expected, actual);
        expected = 100;
        actual = operationHandlerSupply
                .getOperationHandler(DEFAULT_QUANTITY, DEFAULT_VALUE);
        assertEquals(expected, actual);
    }
}
