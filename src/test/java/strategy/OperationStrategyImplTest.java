package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;
import strategy.handler.BalanceHandler;
import strategy.handler.PurchaseHandler;
import strategy.handler.ReturnHandler;
import strategy.handler.SupplyHandler;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap;

    @Before
    public void setUp() {
        transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());

        operationStrategy = new OperationStrategyImpl(transactionHandlerMap);
    }

    @Test
    public void get_HandlerFromStrategy_Ok() {
        TransactionHandler expected = new BalanceHandler();
        TransactionHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void get_HandlerByNull_NotOk() {
        operationStrategy.get(null);
        fail(RuntimeException.class.getName());
    }

    @Test(expected = RuntimeException.class)
    public void get_NotExistHandlerForOperation_NotOk() {
        transactionHandlerMap.remove(FruitTransaction.Operation.BALANCE);
        operationStrategy.get(FruitTransaction.Operation.BALANCE);
        fail(RuntimeException.class.getName());
    }
}
