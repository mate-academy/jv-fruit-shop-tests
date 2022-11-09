package core.basesyntax.strategy.operation.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.FruitTransaction;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WarehouseOperationStrategyImplTest {
    private static final int VALUE = 10;
    private static final int MAP_SIZE_EXPECTED = 4;
    private static final String EMPTY_STRING = "";
    private static Map<String, OperationHandler> auditOperationServiceMap;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        Map<String, OperationHandler> auditOperationServiceMap = new HashMap<>();
        auditOperationServiceMap.put(Operation.BALANCE.getOperation(),
                new BalanceOperationImpl());
        auditOperationServiceMap.put(Operation.SUPPLY.getOperation(),
                new SupplyOperationImpl());
        auditOperationServiceMap.put(Operation.PURCHASE.getOperation(),
                new PurchaseOperationImpl());
        auditOperationServiceMap.put(Operation.RETURN.getOperation(),
                new ReturnOperationImpl());
        fruitTransaction = new FruitTransaction(Operation.BALANCE.getOperation(),
                VALUE, EMPTY_STRING);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_invalidData_notOk() {
        auditOperationServiceMap.get(fruitTransaction.getOperation())
                .applyOperation(fruitTransaction.getFruit(),
                        fruitTransaction.getQuantity());
    }
    
    @Test
    public void warehouseOperation_validData_ok() {
        Map<String, OperationHandler> auditOperationServiceMap = new HashMap<>();
        auditOperationServiceMap.put(Operation.BALANCE.getOperation(),
                new BalanceOperationImpl());
        auditOperationServiceMap.put(Operation.SUPPLY.getOperation(),
                new SupplyOperationImpl());
        auditOperationServiceMap.put(Operation.PURCHASE.getOperation(),
                new PurchaseOperationImpl());
        auditOperationServiceMap.put(Operation.RETURN.getOperation(),
                new ReturnOperationImpl());
        int expected = MAP_SIZE_EXPECTED;
        int actual = auditOperationServiceMap.size();
        Assert.assertEquals(actual, expected);
    }
}
