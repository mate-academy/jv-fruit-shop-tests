package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.operations.BalanceOperationHandler;
import core.basesyntax.strategy.operations.PurchaseOperationHandler;
import core.basesyntax.strategy.operations.ReturnOperationHandler;
import core.basesyntax.strategy.operations.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String BALANCE = "b";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String SUPPLY = "s";
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        Map<String, OperationHandler> map = new HashMap<>();
        map.put(BALANCE, new BalanceOperationHandler());
        map.put(PURCHASE, new PurchaseOperationHandler());
        map.put(RETURN, new ReturnOperationHandler());
        map.put(SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategy(map);
    }

    @Test
    public void getOperationEmptyTest_ok() {
        operationStrategy.getByOperation(null);
    }

    @Test
    public void getByOperationBalanceTest_ok() {
        OperationHandler expected = new BalanceOperationHandler();
        OperationHandler actualHandler = operationStrategy.getByOperation(BALANCE);
        assertEquals(expected.getClass(), actualHandler.getClass());
    }

    @Test
    public void getByOperationPurchaseTest_ok() {
        OperationHandler expected = new PurchaseOperationHandler();
        OperationHandler actualHandler = operationStrategy.getByOperation(PURCHASE);
        assertEquals(expected.getClass(), actualHandler.getClass());
    }

    @Test
    public void getByOperationReturnTest_ok() {
        OperationHandler expected = new ReturnOperationHandler();
        OperationHandler actualHandler = operationStrategy.getByOperation(RETURN);
        assertEquals(expected.getClass(), actualHandler.getClass());
    }

    @Test
    public void getByOperationSupplyTest_ok() {
        OperationHandler expected = new SupplyOperationHandler();
        OperationHandler actualHandler = operationStrategy.getByOperation(SUPPLY);
        assertEquals(expected.getClass(), actualHandler.getClass());
    }

    @After
    public void cleanStorage() {
        Storage.storage.clear();
    }
}
