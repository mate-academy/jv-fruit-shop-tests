package core.basesyntax.strategy;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String BALANCE = "b";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String SUPPLY = "s";
    private static OperationStrategy operationStrategy;
    private static Map<String,OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    public void get_validBalanceOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(BALANCE);
        assertEquals(BalanceOperationHandler.class, actual.getClass());
    }

    @Test
    public void get_validPurchaseOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(PURCHASE);
        assertEquals(PurchaseOperationHandler.class, actual.getClass());
    }

    @Test
    public void get_validReturnOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(RETURN);
        assertEquals(ReturnOperationHandler.class, actual.getClass());
    }

    @Test
    public void get_validSupplyOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(SUPPLY);
        assertEquals(SupplyOperationHandler.class, actual.getClass());
    }
}
