package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final String BALANCE_CODE = "b";
    private static final String PURCHASE_CODE = "p";
    private static final String RETURN_CODE = "r";
    private static final String SUPPLY_CODE = "s";
    private static OperationStrategy operationStrategy;

    @Before
    public void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> testMap = new HashMap<>();
        testMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        testMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        testMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        testMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(testMap);
    }

    @Test
    public void get_balanceHandler_ok() {
        OperationHandler expected = new BalanceOperationHandler();
        Assert.assertEquals(expected.getClass(), operationStrategy
                .get(FruitTransaction.Operation.getByCode(BALANCE_CODE)).getClass());
    }

    @Test
    public void get_purchaseHandler_ok() {
        OperationHandler expected = new PurchaseOperationHandler();
        Assert.assertEquals(expected.getClass(), operationStrategy
                .get(FruitTransaction.Operation.getByCode(PURCHASE_CODE)).getClass());
    }

    @Test
    public void get_returnHandler_ok() {
        OperationHandler expected = new ReturnOperationHandler();
        Assert.assertEquals(expected.getClass(), operationStrategy
                .get(FruitTransaction.Operation.getByCode(RETURN_CODE)).getClass());
    }

    @Test
    public void get_supplyHandler_ok() {
        OperationHandler expected = new SupplyOperationHandler();
        Assert.assertEquals(expected.getClass(), operationStrategy
                .get(FruitTransaction.Operation.getByCode(SUPPLY_CODE)).getClass());
    }
}
