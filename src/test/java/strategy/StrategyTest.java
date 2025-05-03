package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.Operation;

public class StrategyTest {

    private static Map<String, TypeHandler> testHandlers;

    @BeforeClass
    public static void beforeClass() {
        testHandlers = new HashMap<>();
        testHandlers.put(Operation.BALANCE.getType(), new BalanceHandler());
        testHandlers.put(Operation.RETURN.getType(), new ReturnHandler());
        testHandlers.put(Operation.SUPPLY.getType(), new SupplyHandler());
        testHandlers.put(Operation.PURCHASE.getType(), new PurchaseHandler());
    }

    @Test
    public void getOperationBalance_Ok() {
        TypeHandler expected = new BalanceHandler();
        TypeHandler actual = testHandlers.get(Operation.BALANCE.getType());
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getOperationReturn_Ok() {
        TypeHandler expected = new ReturnHandler();
        TypeHandler actual = testHandlers.get(Operation.RETURN.getType());
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getOperationSupply_Ok() {
        TypeHandler expected = new SupplyHandler();
        TypeHandler actual = testHandlers.get(Operation.SUPPLY.getType());
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getOperationPurchase_Ok() {
        TypeHandler expected = new PurchaseHandler();
        TypeHandler actual = testHandlers.get(Operation.PURCHASE.getType());
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getNewOperation_Ok() {
        assertNull(testHandlers.get("l"));
    }

    @Test
    public void getEmptyString_Ok() {
        assertNull(testHandlers.get(""));
    }

    @Test
    public void getNull_OK() {
        assertNull(testHandlers.get(null));
    }
}
