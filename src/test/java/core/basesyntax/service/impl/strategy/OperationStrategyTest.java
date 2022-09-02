package core.basesyntax.service.impl.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy strategy;

    @BeforeClass
    public static void beforeClass() {
        Map<String, OperationHandler> map = new HashMap<>();
        map.put("b", new BalanceOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("p", new PurchaseOperationHandler());
        map.put("r", new ReturnOperationHandler());
        strategy = new OperationStrategy(map);
    }

    @Test
    public void getByOperation_validOperation_Ok() {
        String operation = "s";
        OperationHandler expected = new SupplyOperationHandler();
        OperationHandler actual = strategy.getByOperation(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getByOperation_nullOperation_NotOk() {
        String operation = null;
        assertEquals(null, strategy.getByOperation(operation));
    }

    @Test
    public void getByOperation_nonValidOperation_NotOk() {
        String operation = "bspr";
        assertEquals(null, strategy.getByOperation(operation));
    }

}
