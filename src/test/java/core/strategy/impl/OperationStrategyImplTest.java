package core.strategy.impl;

import core.dao.FruitDao;
import core.dao.FruitDaoImpl;
import core.strategy.Operation;
import core.strategy.OperationHandler;
import core.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static FruitDao fruitDao;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        Map<Operation, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(Operation.BALANCE, new BalanceOperationImpl(fruitDao));
        operationMap.put(Operation.PURCHASE, new PurchaseOperationImpl(fruitDao));
        operationMap.put(Operation.RETURN, new AddOperationImpl(fruitDao));
        operationMap.put(Operation.SUPPLY, new AddOperationImpl(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationMap);
    }

    @Test
    public void get_balanceOperation_ok() {
        OperationHandler expended = new BalanceOperationImpl(fruitDao);
        OperationHandler actual = operationStrategy.get(Operation.BALANCE);
        Assert.assertEquals(expended.getClass(), actual.getClass());
    }

    @Test
    public void get_purchaseOperation_ok() {
        OperationHandler expended = new PurchaseOperationImpl(fruitDao);
        OperationHandler actual = operationStrategy.get(Operation.PURCHASE);
        Assert.assertEquals(expended.getClass(), actual.getClass());
    }

    @Test
    public void get_supplyOperation_ok() {
        OperationHandler expended = new AddOperationImpl(fruitDao);
        OperationHandler actual = operationStrategy.get(Operation.SUPPLY);
        Assert.assertEquals(expended.getClass(), actual.getClass());
    }

    @Test
    public void get_returnOperation_ok() {
        OperationHandler expended = new AddOperationImpl(fruitDao);
        OperationHandler actual = operationStrategy.get(Operation.RETURN);
        Assert.assertEquals(expended.getClass(), actual.getClass());
    }
}
