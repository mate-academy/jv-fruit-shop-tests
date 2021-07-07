package core.basesyntax.service.impl;

import core.basesyntax.service.Strategy;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String EMPTY_STRING = "";
    private static final String OPERATION_BALANCE = "b";
    private static final String OPERATION_SUPPLY = "s";
    private static final String OPERATION_PURCHASE = "p";
    private static final String OPERATION_RETURN = "r";
    private static Map<String, OperationHandler> test_handlers;
    private Strategy strategy = new OperationStrategy(test_handlers);

    @BeforeClass
    public static void beforeClass() {
        test_handlers = new HashMap<>();
        test_handlers.put(OPERATION_BALANCE, new BalanceOperationHandler());
        test_handlers.put(OPERATION_SUPPLY, new AddOperationHandler());
        test_handlers.put(OPERATION_PURCHASE, new PurchaseOperationHandler());
        test_handlers.put(OPERATION_RETURN, new AddOperationHandler());

    }

    @Test
    public void getOperationBalance_Ok() {
        OperationHandler expected = new BalanceOperationHandler();
        OperationHandler actual = strategy.get("b");
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getOperationAdd_Ok() {
        OperationHandler expected = new AddOperationHandler();
        OperationHandler actual = strategy.get("s");
        Assert.assertEquals(expected.getClass(), actual.getClass());

        actual = strategy.get("r");
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getOperationPurchase_Ok() {
        OperationHandler expected = new PurchaseOperationHandler();
        OperationHandler actual = strategy.get("p");
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getUnknownOperation_Ok() {
        Assert.assertNull(strategy.get("z"));
        Assert.assertNull(strategy.get("М╤йщ"));
    }

    @Test
    public void getEmptyString_Ok() {
        Assert.assertNull(strategy.get(EMPTY_STRING));
    }

    @Test
    public void getNull_OK() {
        Assert.assertNull(strategy.get(null));
    }

}
