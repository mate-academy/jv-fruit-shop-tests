package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap;
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
    }

    @Test
    public void getMethod_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        OperationHandler expected = new BalanceOperationHandler();
        Assert.assertEquals(expected instanceof BalanceOperationHandler,
                actual instanceof BalanceOperationHandler);
    }
}
