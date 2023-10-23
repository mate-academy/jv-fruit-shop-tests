package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlers;

    @BeforeClass
    public static void beforeClass() throws Exception {
        handlers = new HashMap<>();
        strategy = new OperationStrategyImpl(handlers);
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    }

    @Test
    public void getBalanceOperationHandler_ok() {
        OperationHandler actual = strategy.getOperationHandler(FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(BalanceOperationHandler.class, actual.getClass());
    }

    @Test
    public void getSupplyOperationHandler_ok() {
        OperationHandler actual = strategy.getOperationHandler(FruitTransaction.Operation.SUPPLY);
        Assert.assertEquals(SupplyOperationHandler.class, actual.getClass());
    }

    @Test
    public void getPurchaseOperationHandler_ok() {
        OperationHandler actual = strategy.getOperationHandler(FruitTransaction.Operation.PURCHASE);
        Assert.assertEquals(PurchaseOperationHandler.class, actual.getClass());
    }

    @Test
    public void getReturnOperationHandler_ok() {
        OperationHandler actual = strategy.getOperationHandler(FruitTransaction.Operation.RETURN);
        Assert.assertEquals(ReturnOperationHandler.class, actual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void getOperationHandlerByUnknownOperation_notOk() {
        strategy.getOperationHandler(null);
    }
}
