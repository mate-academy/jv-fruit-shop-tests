package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getBalance_Ok() {
        OperationHandler actual = operationStrategy.get(
                FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(BalanceOperationHandler.class, actual.getClass());
    }

    @Test
    public void getPurchase_Ok() {
        OperationHandler actual = operationStrategy.get(
                FruitTransaction.Operation.PURCHASE);
        Assert.assertEquals(PurchaseOperationHandler.class, actual.getClass());
    }

    @Test
    public void getSupply_Ok() {
        OperationHandler actual = operationStrategy.get(
                FruitTransaction.Operation.SUPPLY);
        Assert.assertEquals(SupplyOperationHandler.class, actual.getClass());
    }

    @Test
    public void getReturn_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assert.assertEquals(ReturnOperationHandler.class, actual.getClass());
    }
}
