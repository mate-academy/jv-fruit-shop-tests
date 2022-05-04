package strategy;

import static org.junit.Assert.assertNotEquals;

import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyTest {
    private static Strategy strategy;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static SupplyOperationHandler supplyOperationHandler;
    private static ReturnOperationHandler returnOperationHandler;
    private static PurchaseOperationHandler purchaseOperationHandler;
    private static BalanceOperationHandler balanceOperationHandler;
    private static String typeOperation;

    @BeforeClass
    public static void beforeClass() {
        strategy = new Strategy();
        returnOperationHandler = new ReturnOperationHandler();
        supplyOperationHandler = new SupplyOperationHandler();
        purchaseOperationHandler = new PurchaseOperationHandler();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void get_invalidOutput_Ok() {
        OperationHandler expected = purchaseOperationHandler;
        OperationHandler actual = strategy.get("r");
        assertNotEquals(expected, actual);
    }
}
