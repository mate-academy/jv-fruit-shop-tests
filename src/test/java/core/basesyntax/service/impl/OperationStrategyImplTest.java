package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        OperationHandler expected = operationHandlerMap.get(FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        OperationHandler expected = operationHandlerMap.get(FruitTransaction.Operation.PURCHASE);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void get_returnOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        OperationHandler expected = operationHandlerMap.get(FruitTransaction.Operation.RETURN);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void get_supplyOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        OperationHandler expected = operationHandlerMap.get(FruitTransaction.Operation.SUPPLY);
        Assert.assertEquals(actual, expected);
    }
}
