package core.basesyntax;

import static core.basesyntax.Main.BALANCE;
import static core.basesyntax.Main.PURCHASE;
import static core.basesyntax.Main.RETURN;
import static core.basesyntax.Main.SUPPLY;

import core.basesyntax.dto.ShopOperation;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.SubtractOperationHandler;
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
    public void getAddHandler_Ok() {
        Class<? extends OperationHandler> expected = AddOperationHandler.class;
        Class<? extends OperationHandler> actual = strategy
                .getHandler(new ShopOperation("s", "apple", 34)).getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getBalanceHandler_Ok() {
        Class<? extends OperationHandler> expected = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actual = strategy
                .getHandler(new ShopOperation("b", "apple", 34)).getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSubtractHandler_Ok() {
        Class<? extends OperationHandler> expected = SubtractOperationHandler.class;
        Class<? extends OperationHandler> actual = strategy
                .getHandler(new ShopOperation("p", "apple", 34)).getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUnknownHandler_Ok() {
        OperationHandler expected = null;
        OperationHandler actual = strategy.getHandler(new ShopOperation("j", "apple", 34));
        Assert.assertNull(actual);
    }
}
