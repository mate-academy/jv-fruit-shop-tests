package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.impl.BalanceHandler;
import core.basesyntax.operation.impl.PurchaseHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Before
    public void setUp() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(new FruitDaoImpl()));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(new FruitDaoImpl()));
    }

    @Test
    public void process_nullOperation_notOK() {
        try {
            operationStrategy.process(null);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void process_goodOperation_OK() {
        OperationHandler handler = operationStrategy.process(FruitTransaction.Operation.BALANCE);
        Assert.assertSame(BalanceHandler.class, handler.getClass());
    }
}
