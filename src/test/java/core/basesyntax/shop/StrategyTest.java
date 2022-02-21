package core.basesyntax.shop;

import static core.basesyntax.shop.MainApp.BALANCE;
import static core.basesyntax.shop.MainApp.PURCHASE;
import static core.basesyntax.shop.MainApp.RETURN;
import static core.basesyntax.shop.MainApp.SUPPLY;

import core.basesyntax.shop.impl.FruitTransaction;
import core.basesyntax.shop.strategy.AddOperationHandler;
import core.basesyntax.shop.strategy.BalanceOperationHandler;
import core.basesyntax.shop.strategy.OperationHandler;
import core.basesyntax.shop.strategy.Strategy;
import core.basesyntax.shop.strategy.SubtractOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyTest {
    private static Strategy strategy;
    private static Map<String, OperationHandler> handlers;

    @BeforeClass
    public static void beforeClass() {
        handlers = new HashMap<>();
        handlers.put(BALANCE, new BalanceOperationHandler());
        handlers.put(SUPPLY, new AddOperationHandler());
        handlers.put(RETURN, new AddOperationHandler());
        handlers.put(PURCHASE, new SubtractOperationHandler());
        strategy = new Strategy(handlers);
    }

    @Test(expected = NullPointerException.class)
    public void getHandler_nullParameter_NotOk() {
        strategy.getHandler(null);
    }

    @Test
    public void get_addHandler_Ok() {
        Class<? extends OperationHandler> expected = AddOperationHandler.class;
        Class<? extends OperationHandler> actual = strategy
                .getHandler(new FruitTransaction("s", "apple", 25)).getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_balanceHandler_Ok() {
        Class<? extends OperationHandler> expected = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actual = strategy
                .getHandler(new FruitTransaction("b", "apple", 25)).getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_subtractHandler_Ok() {
        Class<? extends OperationHandler> expected = SubtractOperationHandler.class;
        Class<? extends OperationHandler> actual = strategy
                .getHandler(new FruitTransaction("p", "apple", 25)).getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_unknownHandler_Ok() {
        OperationHandler expected = null;
        OperationHandler actual = strategy.getHandler(new FruitTransaction("j", "apple", 25));
        Assert.assertNull(actual);
    }
}
