package core.basesyntax.strategy.impl;

import core.basesyntax.handler.impl.OperationHandler;
import core.basesyntax.handler.impl.impl.AddOperationHandler;
import core.basesyntax.handler.impl.impl.BalanceOperationHandler;
import core.basesyntax.handler.impl.impl.RemoveOperationHandler;
import core.basesyntax.strategy.impl.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> handlerMap;

    @BeforeClass
    public static void beforeClass() {
        handlerMap = new HashMap<>();
        handlerMap.put("b", new BalanceOperationHandler());
        handlerMap.put("s", new AddOperationHandler());
        handlerMap.put("r", new AddOperationHandler());
        handlerMap.put("p", new RemoveOperationHandler());
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void operationStrategy_usualState_ok() {
        Class<? extends AddOperationHandler> expected = AddOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy.get("s").getClass();
        Assert.assertEquals(expected, actual);
    }
}
