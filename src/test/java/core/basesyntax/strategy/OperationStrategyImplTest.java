package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.service.amount.BalanceHandler;
import core.basesyntax.service.amount.OperationHandler;
import core.basesyntax.service.amount.PurchaseHandler;
import core.basesyntax.service.amount.ReturnHandler;
import core.basesyntax.service.amount.SupplyHandler;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static Map<Operation, Class> operationHandlerMap;

    @BeforeClass
    public static void setUp() {
        operationHandlerMap = Map.of(Operation.RETURN, ReturnHandler.class,
                Operation.BALANCE, BalanceHandler.class,
                Operation.PURCHASE, PurchaseHandler.class,
                Operation.SUPPLY, SupplyHandler.class);
    }

    @Test
    public void strategyTest() {
        OperationStrategy strategy = new OperationStrategyImpl();
        for (Map.Entry<Operation, Class> entry: operationHandlerMap.entrySet()) {
            OperationHandler actual = strategy.getOperationHandler(entry.getKey());
            Class expected = entry.getValue();
            Assert.assertEquals(expected, actual.getClass());
        }
    }

}
