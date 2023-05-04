package strategy;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import operation.BalanceHandler;
import operation.OperationHandler;
import operation.PurchaseHandler;
import operation.ReturnHandler;
import operation.SupplyHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHashMap;
    private static FruitStrategyImpl fruitStrategy;
    private static FruitTransaction fruitTransaction;

    @Before
    public void before() throws Exception {
        operationHashMap = new HashMap<>();
        operationHashMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHashMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHashMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationHashMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        fruitStrategy = new FruitStrategyImpl(operationHashMap);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void handleBalance_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitStrategy.proceed(fruitTransaction);
        FruitTransaction.Operation actual = fruitTransaction.getOperation();
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handlePurchase_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitStrategy.proceed(fruitTransaction);
        FruitTransaction.Operation actual = fruitTransaction.getOperation();
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handleReturn_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitStrategy.proceed(fruitTransaction);
        FruitTransaction.Operation actual = fruitTransaction.getOperation();
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handleSupply_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitStrategy.proceed(fruitTransaction);
        FruitTransaction.Operation actual = fruitTransaction.getOperation();
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        Assert.assertEquals(expected, actual);
    }
}
