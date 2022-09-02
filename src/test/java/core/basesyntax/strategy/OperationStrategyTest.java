package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String BALANCE = "b";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String SUPPLY = "s";
    private OperationStrategy operationStrategy;
    private OperationHandler balanceOperationHandler;
    private OperationHandler purchaseOperationHandler;
    private OperationHandler returnOperationHandler;
    private OperationHandler supplyOperationHandler;
    private Map<String,OperationHandler> operationHandlerMap;

    @Before
    public void setUp() throws Exception {
        balanceOperationHandler = new BalanceOperationHandler();
        purchaseOperationHandler = new PurchaseOperationHandler();
        returnOperationHandler = new ReturnOperationHandler();
        supplyOperationHandler = new SupplyOperationHandler();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(BALANCE,balanceOperationHandler);
        operationHandlerMap.put(PURCHASE,purchaseOperationHandler);
        operationHandlerMap.put(RETURN,returnOperationHandler);
        operationHandlerMap.put(SUPPLY,supplyOperationHandler);
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    public void get_validBalanceOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(BALANCE);
        assertEquals(balanceOperationHandler, actual);
    }

    @Test
    public void get_validPurchaseOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(PURCHASE);
        assertEquals(purchaseOperationHandler, actual);
    }

    @Test
    public void get_validReturnOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(RETURN);
        assertEquals(returnOperationHandler, actual);
    }

    @Test
    public void get_validSupplyOperation_Ok() {
        OperationHandler actual = operationStrategy.getByOperation(SUPPLY);
        assertEquals(supplyOperationHandler, actual);
    }
}
