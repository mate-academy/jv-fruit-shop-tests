package myfirstproject.service.strategy;

import java.util.HashMap;
import java.util.Map;
import myfirstproject.model.Operation;
import myfirstproject.strategy.BalanceOperation;
import myfirstproject.strategy.OperationHandler;
import myfirstproject.strategy.PurchaseOperation;
import myfirstproject.strategy.ReturnOperation;
import myfirstproject.strategy.SupplyOperation;
import org.junit.BeforeClass;
import org.junit.Test;

public class InitializingTest {
    private static Map<String, OperationHandler> operation;

    @BeforeClass
    public static void setUp() {
        operation = new HashMap<>();
    }

    @Test
    public void testBalance_Ok() {
        operation.put(Operation.BALANCE.getOperation(), new BalanceOperation());
    }

    @Test
    public void testSupply_Ok() {
        operation.put(Operation.SUPPLY.getOperation(), new SupplyOperation());
    }

    @Test
    public void testPurchase_Ok() {
        operation.put(Operation.PURCHASE.getOperation(), new PurchaseOperation());
    }

    @Test
    public void testReturn_Ok() {
        operation.put(Operation.RETURN.getOperation(), new ReturnOperation());
    }

}
