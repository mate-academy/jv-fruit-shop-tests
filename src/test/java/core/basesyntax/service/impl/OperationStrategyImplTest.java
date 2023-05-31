package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operations.BalanceOperationHandler;
import core.basesyntax.service.operations.PurchaseOperationHandler;
import core.basesyntax.service.operations.ReturnOperationHandler;
import core.basesyntax.service.operations.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;
    private static Map<Operation, OperationHandler> strategies;

    @BeforeClass
    public static void beforeClass() {
        strategies = new HashMap<>();
    }

    @Before
    public void before() {
        strategies.put(Operation.BALANCE, new BalanceOperationHandler());
        strategies.put(Operation.SUPPLY, new SupplyOperationHandler());
        strategies.put(Operation.PURCHASE, new PurchaseOperationHandler());
        strategies.put(Operation.RETURN, new ReturnOperationHandler());
        strategy = new OperationStrategyImpl(strategies);
    }

    @Test
    public void operationStrategy_Ok() {
        OperationHandler expected = strategies.get(Operation.BALANCE);
        OperationHandler actual = strategy.get(Operation.BALANCE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operationStrategy_Not_Ok() {
        OperationHandler expected = strategies.get(Operation.BALANCE);
        OperationHandler actual = strategy.get(Operation.RETURN);
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void getPurchaseOperationHandler_Ok() {
        OperationHandler actual = strategies.get(Operation.PURCHASE);
        Assert.assertEquals(PurchaseOperationHandler.class, actual.getClass());
    }

    @Test
    public void getSupplyOperationHandler_Ok() {
        OperationHandler actual = strategies.get(Operation.SUPPLY);
        Assert.assertEquals(SupplyOperationHandler.class, actual.getClass());
    }

    @Test
    public void getReturnOperationHandler_Ok() {
        OperationHandler actual = strategies.get(Operation.RETURN);
        Assert.assertEquals(ReturnOperationHandler.class, actual.getClass());
    }

    @Test
    public void getBalanceOperationHandler_Ok() {
        OperationHandler actual = strategies.get(Operation.BALANCE);
        Assert.assertEquals(BalanceOperationHandler.class, actual.getClass());
    }
}
