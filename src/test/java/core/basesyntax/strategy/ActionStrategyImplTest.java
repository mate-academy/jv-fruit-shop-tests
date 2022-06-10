package core.basesyntax.strategy;

import core.basesyntax.exception.OperationUnknownException;
import core.basesyntax.model.ProductTransaction;
import core.basesyntax.strategy.action.ActionHandler;
import core.basesyntax.strategy.action.BalanceHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActionStrategyImplTest {
    private static ActionStrategy actionStrategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<ProductTransaction.Operation, ActionHandler> actionHandlerMap = new HashMap<>();
        actionHandlerMap.put(ProductTransaction.Operation.BALANCE, new BalanceHandler());
        actionStrategy = new ActionStrategyImpl(actionHandlerMap);
    }

    @Test
    public void get_operationInMap_ok() {
        ActionHandler actual = actionStrategy.get(ProductTransaction.Operation.BALANCE);
        Assert.assertEquals(BalanceHandler.class, actual.getClass());
    }

    @Test(expected = OperationUnknownException.class)
    public void get_operationNotInMap_notOk() {
        actionStrategy.get(ProductTransaction.Operation.RETURN);
    }
}
