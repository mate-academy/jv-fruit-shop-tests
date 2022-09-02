package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyHandlerTest {
    private static OperationStrategyHandler strategy;

    @BeforeAll
    public static void init() {
        Map<String, OperationHandler> map = new HashMap<>();
        map.put("b", new BalanceOperationHandler());
        map.put("p", new PurchaseOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("r", new SupplyOperationHandler());
        strategy = new OperationStrategyHandler(map);
    }

    @Test
    void getByOperationBalanceOperation_Ok() {
        String operation = "b";
        OperationHandler expected = new BalanceOperationHandler();
        OperationHandler actual = strategy.getByOperation(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getByOperationPurchaseOperation_Ok() {
        String operation = "p";
        OperationHandler expected = new PurchaseOperationHandler();
        OperationHandler actual = strategy.getByOperation(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getByOperationSupplyOperation_Ok() {
        String operation = "s";
        OperationHandler expected = new SupplyOperationHandler();
        OperationHandler actual = strategy.getByOperation(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getByOperationReturnOperation_Ok() {
        String operation = "r";
        OperationHandler expected = new SupplyOperationHandler();
        OperationHandler actual = strategy.getByOperation(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
