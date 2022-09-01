package core.basesyntax.strategy;

import static org.junit.Assert.assertTrue;

import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> map;

    @BeforeClass
    public static void beforeClass() {
        map = new HashMap<>();
        map.put("b", new BalanceOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("p", new PurchaseOperationHandler());
        map.put("r", new ReturnOperationHandler());
        operationStrategy = new OperationStrategy(map);
    }

    @Test
    public void operationHandler_BalanceHandlerCall_Ok() {
        assertTrue(operationStrategy.getByOperation("b").getClass()
                == BalanceOperationHandler.class);
    }

    @Test
    public void operationHandler_SupplyHandlerCall_Ok() {
        assertTrue(operationStrategy.getByOperation("s").getClass()
                == SupplyOperationHandler.class);
    }

    @Test
    public void operationHandler_PurchaseHandlerCall_Ok() {
        assertTrue(operationStrategy.getByOperation("p").getClass()
                == PurchaseOperationHandler.class);
    }

    @Test
    public void operationHandler_ReturnHandlerCall_Ok() {
        assertTrue(operationStrategy.getByOperation("r").getClass()
                == ReturnOperationHandler.class);
    }
}
