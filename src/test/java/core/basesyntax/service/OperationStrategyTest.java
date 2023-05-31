package core.basesyntax.service;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy strategy;
    private static Map<FruitTransaction.Operation, OperationHandler> strategies;

    @BeforeClass
    public static void beforeClass() {
        strategies = new HashMap<>();
    }

    @Before
    public void setUp() {
        strategies.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        strategies.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        strategies.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        strategies.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        strategy = new OperationStrategyImpl(strategies);
    }

    @Test(expected = RuntimeException.class)
    public void get_null_NotOk() {
        strategy.get(null);
    }

    @Test
    public void get_Ok() {
        OperationHandler expected = strategies.get(FruitTransaction.Operation.BALANCE);
        OperationHandler actual = strategy.get(FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(expected, actual);
    }
}
