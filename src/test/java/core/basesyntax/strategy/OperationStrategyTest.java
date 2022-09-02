package core.basesyntax.strategy;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> map;

    @Before
    public void setUp() throws Exception {
        map = new HashMap<>();
        map.put("b", new BalanceOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("p", new PurchaseOperationHandler());
        map.put("r", new ReturnOperationHandler());
        operationStrategy = new OperationStrategy(map);
    }

    @Test
    public void getByOperation_checkBalanceOperation_Ok() {
        Class<?> expected = BalanceOperationHandler.class;
        Class<?> actual = operationStrategy.getByOperation("b").getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getByOperation_checkSupplyOperation_Ok() {
        Class<?> expected = SupplyOperationHandler.class;
        Class<?> actual = operationStrategy.getByOperation("s").getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getByOperation_checkPurchaseOperation_Ok() {
        Class<?> expected = PurchaseOperationHandler.class;
        Class<?> actual = operationStrategy.getByOperation("p").getClass();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getByOperation_checkReturnOperation_Ok() {
        Class<?> expected = ReturnOperationHandler.class;
        Class<?> actual = operationStrategy.getByOperation("r").getClass();
        Assert.assertEquals(expected, actual);
    }
}
