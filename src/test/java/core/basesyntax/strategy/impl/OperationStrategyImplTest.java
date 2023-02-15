package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationOperationHandlerMap;

    @Before
    public void setUp() {
        operationOperationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceStrategyOperationImpl(),
                FruitTransaction.Operation.SUPPLY, new SupplyStrategyOperationImpl(),
                FruitTransaction.Operation.PURCHASE, new PurchaseStrategyOperationImpl(),
                FruitTransaction.Operation.RETURN, new ReturnStrategyOperationImpl()
        );
        operationStrategy = new OperationStrategyImpl(operationOperationHandlerMap);
    }

    @Test
    public void get_purchase_Ok() {
        OperationHandler expected
                = operationOperationHandlerMap.get(FruitTransaction.Operation.PURCHASE);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_supply_Ok() {
        OperationHandler expected
                = operationOperationHandlerMap.get(FruitTransaction.Operation.SUPPLY);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_return_Ok() {
        OperationHandler expected
                = operationOperationHandlerMap.get(FruitTransaction.Operation.RETURN);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_balance_Ok() {
        OperationHandler expected
                = operationOperationHandlerMap.get(FruitTransaction.Operation.BALANCE);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_null_notOk() {
        operationStrategy.get(null);
    }
}
