package core.basesyntax.strategy;

import core.basesyntax.service.BalanceOperationHandler;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.PurchaseOperationHandler;
import core.basesyntax.service.RetureOperationHandler;
import core.basesyntax.service.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class OperationStrategyImplTest {

    @Test
    public void operationStrategy_correctData_Ok() {
        Map<String, OperationHandler> handlersMap = new HashMap<>();
        handlersMap.put("b",new BalanceOperationHandler());
        handlersMap.put("s",new SupplyOperationHandler());
        handlersMap.put("p",new PurchaseOperationHandler());
        handlersMap.put("r",new RetureOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlersMap);
        OperationHandler handler = operationStrategy.get("b");
        Class<? extends OperationHandler> clazz = handler.getClass();
        Assert.assertEquals(BalanceOperationHandler.class, clazz);
    }
}
