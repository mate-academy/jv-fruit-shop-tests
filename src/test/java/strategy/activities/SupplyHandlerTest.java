package strategy.activities;

import static org.junit.Assert.assertEquals;

import db.FruitStore;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final String OPERATION_TYPE = "s";
    private static ActivitiesHandler handler = new SupplyHandler();
    private static Map<String, Integer> expected = new HashMap<>();

    @Test
    public void operationWithValidDataOk() {
        expected.put("banana", 20);
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 20));
        assertEquals(expected, FruitStore.supplies);
    }

    @Test
    public void threeOperationsWithValidDataOk() {
        expected.put("banana", 30);
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 20));
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 7));
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 3));
        assertEquals(expected, FruitStore.supplies);
    }

    @Test (expected = RuntimeException.class)
    public void operationFromNullNotOk() {
        handler.operation(null);
    }

    @After
    public void tearDown() {
        FruitStore.supplies.clear();
        expected.clear();
    }
}
