package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.AmountHandler;
import core.basesyntax.strategy.AmountStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AmountStrategyImplTest {
    private static AmountStrategy strategy;
    private static AmountHandler amountHandler;
    private static final Fruit TEST_FRUIT = new Fruit("carrot", 25);
    private static final String OPERATION_BALANCE = "b";
    private static final String OPERATION_PURCHASE = "p";
    private static final String OPERATION_RETURN = "r";
    private static final String OPERATION_SUPPLY = "s";

    @BeforeClass
    public static void setUp() {
        Map<String, AmountHandler> amountHandlerMap = new HashMap<>();
        amountHandlerMap.put("b", new BalanceAmountHandler());
        amountHandlerMap.put("p", new PurchaseAmountHandler());
        amountHandlerMap.put("r", new ReturnAmountHandler());
        amountHandlerMap.put("s", new SupplyAmountHandler());
        strategy = new AmountStrategyImpl(amountHandlerMap);
    }

    @Test
    public void amountStrategy_applyingStrategyOperation_ok() {
        Class<? extends AmountHandler> actual = strategy.get(OPERATION_BALANCE).getClass();
        Class<? extends AmountHandler> expected = BalanceAmountHandler.class;
        Assert.assertEquals(expected, actual);
        actual = strategy.get(OPERATION_PURCHASE).getClass();
        expected = PurchaseAmountHandler.class;
        Assert.assertEquals(expected, actual);
        actual = strategy.get(OPERATION_RETURN).getClass();
        expected = ReturnAmountHandler.class;
        Assert.assertEquals(expected, actual);
        actual = strategy.get(OPERATION_SUPPLY).getClass();
        expected = SupplyAmountHandler.class;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
