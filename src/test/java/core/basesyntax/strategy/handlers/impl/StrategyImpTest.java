package core.basesyntax.strategy.handlers.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.Strategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StrategyImpTest {

    private final Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();

    private final Strategy strategy = new StrategyImp(handlers);

    @Before
    public void put() {
        handlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY,new
                SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE,new
                PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN,new
                ReturnOperationHandler());
    }

    @Test
    public void get_correctHandler_OK() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",20);
        OperationHandler expected = new BalanceOperationHandler();
        OperationHandler actual = strategy.getHandler(fruitTransaction);
        Assert.assertEquals(expected.getClass(),actual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void fruitTransaction_isNull_not_OK() {
        FruitTransaction fruitTransaction = null;
        strategy.getHandler(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void fieldOperation_isNull_not_OK() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(null,
                        "banana",20);
        OperationHandler expected = new BalanceOperationHandler();
        OperationHandler actual = strategy.getHandler(fruitTransaction);
        Assert.assertEquals(expected.getClass(),actual.getClass());
    }

    @After
    public void clear() {
        handlers.clear();
    }
}
