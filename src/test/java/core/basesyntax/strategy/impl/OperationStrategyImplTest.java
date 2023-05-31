package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.impl.BalanceOperation;
import core.basesyntax.handler.impl.PurchaseOperation;
import core.basesyntax.handler.impl.ReturnOperation;
import core.basesyntax.handler.impl.SupplyOperation;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static FruitDao fruitDao;
    private static OperationStrategyImpl operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerOperationMap = new HashMap<>();

        BalanceOperation balanceOperation = new BalanceOperation(fruitDao);
        PurchaseOperation purchaseOperation = new PurchaseOperation(fruitDao);
        ReturnOperation returnOperation = new ReturnOperation(fruitDao);
        SupplyOperation supplyOperation = new SupplyOperation(fruitDao);

        handlerOperationMap.put(
                FruitTransaction.Operation.BALANCE,balanceOperation);
        handlerOperationMap.put(
                FruitTransaction.Operation.SUPPLY, supplyOperation);
        handlerOperationMap.put(
                FruitTransaction.Operation.PURCHASE, purchaseOperation);
        handlerOperationMap.put(
                FruitTransaction.Operation.RETURN, returnOperation);

        operationStrategy = new OperationStrategyImpl(handlerOperationMap);
    }

    @Test
    public void getHandler_balanceOperation_Ok() {
        OperationHandler expected = new BalanceOperation(fruitDao);
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_supplyOperation_Ok() {
        OperationHandler expected = new SupplyOperation(fruitDao);
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_purchaseOperation_Ok() {
        OperationHandler expected = new PurchaseOperation(fruitDao);
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_returnOperation_Ok() {
        OperationHandler expected = new ReturnOperation(fruitDao);
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }
}
