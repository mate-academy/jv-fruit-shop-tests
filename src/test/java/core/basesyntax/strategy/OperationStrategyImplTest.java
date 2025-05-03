package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
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

        BalanceOperationHandler balanceOperation = new BalanceOperationHandler(fruitDao);
        PurchaseOperationHandler purchaseOperation = new PurchaseOperationHandler(fruitDao);
        ReturnOperationHandler returnOperation = new ReturnOperationHandler(fruitDao);
        SupplyOperationHandler supplyOperation = new SupplyOperationHandler(fruitDao);

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
        OperationHandler expected = new BalanceOperationHandler(fruitDao);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_supplyOperation_Ok() {
        OperationHandler expected = new SupplyOperationHandler(fruitDao);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_purchaseOperation_Ok() {
        OperationHandler expected = new PurchaseOperationHandler(fruitDao);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_returnOperation_Ok() {
        OperationHandler expected = new ReturnOperationHandler(fruitDao);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }
}
