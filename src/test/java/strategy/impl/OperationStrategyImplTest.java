package strategy.impl;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.OperationHandler;
import strategy.OperationStrategy;

public class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler>
            operationHandlerMap = new HashMap<>();
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeAll() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
    }

    @Test
    public void getHandler_validData_Ok() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        OperationHandler operationHandlerActual = operationStrategy.getHandler(
                FruitTransaction.Operation.BALANCE);
        OperationHandler operationHandlerExtended = new BalanceOperationHandler();
        Assert.assertEquals("Some problem with OperationStrategyImpl",
                operationHandlerActual.getClass(),operationHandlerExtended.getClass());
    }
}
