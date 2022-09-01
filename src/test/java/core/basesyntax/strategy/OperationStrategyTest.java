package core.basesyntax.strategy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String BALANCE = "b";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String SUPPLY = "s";
    private Map<String, OperationHandler> map;
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() throws Exception {
        map = new HashMap<>();
        operationStrategy = new OperationStrategy(map);
    }

    @Test
    public void getByOperationBalance_OK() {
        Set<String> expected = new HashSet<>();
        expected.add("b");
        OperationHandler actualHandler = operationStrategy.getByOperation(BALANCE);
        map.put(BALANCE, actualHandler);
        Set<String> actual = map.keySet();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getByOperationPurchase_OK() {
        Set<String> expected = new HashSet<>();
        expected.add("p");
        OperationHandler actualHandler = operationStrategy.getByOperation(PURCHASE);
        map.put(PURCHASE, actualHandler);
        Set<String> actual = map.keySet();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getByOperationReturn_OK() {
        Set<String> expected = new HashSet<>();
        expected.add("r");
        OperationHandler actualHandler = operationStrategy.getByOperation(RETURN);
        map.put(RETURN, actualHandler);
        Set<String> actual = map.keySet();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getByOperationSupply_OK() {
        Set<String> expected = new HashSet<>();
        expected.add("s");
        OperationHandler actualHandler = operationStrategy.getByOperation(SUPPLY);
        map.put(SUPPLY, actualHandler);
        Set<String> actual = map.keySet();
        Assert.assertEquals(expected, actual);
    }
}
