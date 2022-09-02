package core.basesyntax.strategy;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String BALANCE = "b";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String SUPPLY = "s";
    private static Map<String, OperationHandler> map;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        map = new HashMap<>();
        map.put(BALANCE, new BalanceOperationHandler());
        map.put(PURCHASE, new PurchaseOperationHandler());
        map.put(RETURN, new ReturnOperationHandler());
        map.put(SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategy(map);
    }

    @Test
    public void getByOperation_Balance_Ok() {
        OperationHandler expected = new BalanceOperationHandler();
        OperationHandler actual = operationStrategy.getByOperation(BALANCE);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getByOperation_Purchase_Ok() {
        OperationHandler expected = new PurchaseOperationHandler();
        OperationHandler actual = operationStrategy.getByOperation(PURCHASE);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getByOperation_Return_Ok() {
        OperationHandler expected = new ReturnOperationHandler();
        OperationHandler actual = operationStrategy.getByOperation(RETURN);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getByOperation_Supply_Ok() {
        OperationHandler expected = new SupplyOperationHandler();
        OperationHandler actual = operationStrategy.getByOperation(SUPPLY);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }
}
