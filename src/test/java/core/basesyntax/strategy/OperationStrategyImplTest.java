package core.basesyntax.strategy;

import core.basesyntax.service.BalanceOperationHandler;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.PurchaseOperationHandler;
import core.basesyntax.service.RetureOperationHandler;
import core.basesyntax.service.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @Before
    public void beforEachTest() {
        Map<String, OperationHandler> handlersMap = new HashMap<>();
        handlersMap.put("b",new BalanceOperationHandler());
        handlersMap.put("s",new SupplyOperationHandler());
        handlersMap.put("p",new PurchaseOperationHandler());
        handlersMap.put("r",new RetureOperationHandler());
        operationStrategy = new OperationStrategyImpl(handlersMap);
    }

    @Test
    public void operationStrategy_correctData_ok() {
        OperationHandler handler = operationStrategy.get("b");
        Class<? extends OperationHandler> clazz = handler.getClass();
        Assert.assertEquals(BalanceOperationHandler.class, clazz);
    }
}
