package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;
import strategy.handler.BalanceHandler;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static TransactionHandler balanceHandler;

    @Before
    public void setUp() {
        balanceHandler = new BalanceHandler();
        Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);

        operationStrategy = new OperationStrategyImpl(transactionHandlerMap);
    }

    @Test
    public void get_HandlerFromStrategy_Ok() {
        TransactionHandler expected = balanceHandler;
        TransactionHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void get_HandlerByNull_NotOk() {
        operationStrategy.get(null);
        fail(AssertionError.class.getName());
    }
}
