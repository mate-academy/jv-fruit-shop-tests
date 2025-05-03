package strategy;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.impl.BalanceOperationImpl;
import strategy.impl.PurchaseOperationImpl;
import strategy.impl.ReturnOperationImpl;
import strategy.impl.StrategyServiceImpl;
import strategy.impl.SupplyOperationImpl;

public class StrategyServiceTest {
    private static StrategyService strategyService;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationImpl());
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationImpl());
        strategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationImpl());
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationImpl());
        strategyService = new StrategyServiceImpl(strategyMap);
    }

    @Test
    public void strategyService_Purchase_OK() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.getByValue("p"), "apple", 20);
        Class<? extends OperationHandler> expectedClass = PurchaseOperationImpl.class;
        Class<? extends OperationHandler> actualClass =
                strategyService.get(transaction.getOperation()).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void strategyService_Balance_OK() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.getByValue("b"), "orange", 15);
        Class<? extends OperationHandler> expectedClass = BalanceOperationImpl.class;
        Class<? extends OperationHandler> actualClass =
                strategyService.get(transaction.getOperation()).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void strategyService_Supply_OK() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.getByValue("s"), "banana", 4);
        Class<? extends OperationHandler> expectedClass = SupplyOperationImpl.class;
        Class<? extends OperationHandler> actualClass =
                strategyService.get(transaction.getOperation()).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void strategyService_Return_OK() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.getByValue("r"), "apple", 96);
        Class<? extends OperationHandler> expectedClass = ReturnOperationImpl.class;
        Class<? extends OperationHandler> actualClass =
                strategyService.get(transaction.getOperation()).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test(expected = RuntimeException.class)
    public void strategyService_IncorrectOperation_NotOK() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.getByValue("k"), "orange", 13);
        strategyService.get(transaction.getOperation());
    }
}
