package core.basesyntax.service.operation.impl;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<FruitTransaction.Operation, OperationHandler> operationServiceMap = new HashMap<>();
        operationServiceMap.put(FruitTransaction.Operation.BALANCE,new BalanceOperationHandler());
        operationServiceMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationServiceMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationServiceMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationServiceMap);
    }

    @Test
    public void getBalance_ok() {
        assertEquals(BalanceOperationHandler.class,
                operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    public void getSupply_ok() {
        assertEquals(SupplyOperationHandler.class,
                operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass());
    }

    @Test
    public void getPurchase_ok() {
        assertEquals(PurchaseOperationHandler.class,
                operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass());
    }

    @Test
    public void getReturn_ok() {
        assertEquals(ReturnOperationHandler.class,
                operationStrategy.get(FruitTransaction.Operation.RETURN).getClass());
    }
}
