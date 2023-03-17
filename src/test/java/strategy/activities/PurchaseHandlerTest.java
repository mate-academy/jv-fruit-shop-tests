package strategy.activities;

import static org.junit.Assert.assertEquals;

import db.FruitStore;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final String OPERATION_TYPE = "p";
    private static ActivitiesHandler handler = new PurchaseHandler();
    private static Map<String, Integer> expected = new HashMap<>();

    @Test
    public void operationWithValidDataOk() {
        expected.put("banana", 10);
        FruitStore.supplies.put("banana", 30);
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 20));
        assertEquals(expected, FruitStore.supplies);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseOperationWithEmptySupplyNotOk() {
        handler.operation(new FruitTransaction(OPERATION_TYPE, "banana", 20));
    }

    @Test (expected = RuntimeException.class)
    public void operationFromNullNotOk() {
        handler.operation(null);
    }

    @After
    public void tearDown() throws Exception {
        FruitStore.supplies.clear();
        expected.clear();
    }
}
