package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionStrategyTest {
    private static FruitTransactionStrategy fruitTransactionStrategy;
    private static Map<FruitTransaction.Operation,OperationHandler> strategyMap;

    @BeforeClass
    public static void beforeClass() {
        strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        strategyMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        fruitTransactionStrategy = new FruitTransactionStrategyImpl(strategyMap);
    }

    @Test
    public void getBalanceOperationHandler_Ok() {
        OperationHandler actual = fruitTransactionStrategy.get(FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(actual.getClass(), BalanceOperationHandler.class);
    }

    @Test
    public void getReturnOperationHandler_Ok() {
        OperationHandler actual = fruitTransactionStrategy.get(FruitTransaction.Operation.RETURN);
        Assert.assertEquals(actual.getClass(), ReturnOperationHandler.class);
    }

    @Test
    public void getPurchaseOperationHandler_Ok() {
        OperationHandler actual = fruitTransactionStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assert.assertEquals(actual.getClass(), PurchaseOperationHandler.class);
    }

    @Test
    public void getSupplyOperationHandler_Ok() {
        OperationHandler actual = fruitTransactionStrategy.get(FruitTransaction.Operation.SUPPLY);
        Assert.assertEquals(actual.getClass(), SupplyOperationHandler.class);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationHandler_nullInput_notOk() {
        fruitTransactionStrategy.get(null);
    }
}
