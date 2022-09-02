package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.operation.BalaceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy strategy;

    @BeforeClass
    public static void beforeClass() {
        Map<String, OperationHandler> map = new HashMap<>();
        map.put("b", new BalaceOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("p", new PurchaseOperationHandler());
        map.put("r", new ReturnOperationHandler());
        strategy = new OperationStrategy(map);
    }

    @Test
    public void getByOperation_OperationIsValid_Ok() {
        String operation = "s";
        OperationHandler expected = new SupplyOperationHandler();
        OperationHandler actual = strategy.getByOperation(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getByOperation_OperationIsNull_Ok() {
        String operation = null;
        assertEquals(null, strategy.getByOperation(operation));
    }

    @Test
    public void getByOperation_OperationIsNotValid_Ok() {
        String operation = "bspr";
        assertEquals(null, strategy.getByOperation(operation));
    }
}
