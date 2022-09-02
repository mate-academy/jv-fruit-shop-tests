package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String BALANCE = "b";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String SUPPLY = "s";
    private Map<String, OperationHandler> map = new HashMap<>();
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        operationStrategy = new OperationStrategy(map);
    }

    @Test
    public void getOperationEmptyTest_Ok() {
        Set<String> expected = new HashSet<>();
        expected.add(null);
        OperationHandler actualHandler = operationStrategy.getByOperation(null);
        map.put(null, actualHandler);
        Set<String> actual = map.keySet();
        assertEquals(expected, actual);
    }

    @Test
    public void getByOperationBalanceTest_OK() {
        Set<String> expected = new HashSet<>();
        expected.add("b");
        OperationHandler actualHandler = operationStrategy.getByOperation(BALANCE);
        map.put(BALANCE, actualHandler);
        Set<String> actual = map.keySet();
        assertEquals(expected, actual);
    }

    @Test
    public void getByOperationPurchaseTest_OK() {
        Set<String> expected = new HashSet<>();
        expected.add("p");
        OperationHandler actualHandler = operationStrategy.getByOperation(PURCHASE);
        map.put(PURCHASE, actualHandler);
        Set<String> actual = map.keySet();
        assertEquals(expected, actual);
    }

    @Test
    public void getByOperationReturnTest_OK() {
        Set<String> expected = new HashSet<>();
        expected.add("r");
        OperationHandler actualHandler = operationStrategy.getByOperation(RETURN);
        map.put(RETURN, actualHandler);
        Set<String> actual = map.keySet();
        assertEquals(expected, actual);
    }

    @Test
    public void getByOperationSupplyTest_OK() {
        Set<String> expected = new HashSet<>();
        expected.add("s");
        OperationHandler actualHandler = operationStrategy.getByOperation(SUPPLY);
        map.put(SUPPLY, actualHandler);
        Set<String> actual = map.keySet();
        assertEquals(expected, actual);
    }

    @After
    public void cleanStorage() {
        Storage.storage.clear();
    }
}
