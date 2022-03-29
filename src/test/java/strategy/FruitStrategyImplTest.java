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
    private static OperationHandler balanceOperation;
    private static OperationHandler purchaseOperation;
    private static OperationHandler returnOperation;
    private static OperationHandler supplyOperation;

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
        balanceOperation = new BalanceHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        OperationHandler actual = fruitStrategy.proceed(fruitTransaction);
        OperationHandler expected = balanceOperation.handle(fruitTransaction);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handlePurchase_Ok() {
        purchaseOperation = new PurchaseHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        OperationHandler actual = fruitStrategy.proceed(fruitTransaction);
        OperationHandler expected = purchaseOperation.handle(fruitTransaction);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void handleReturn_Ok() {
        returnOperation = new ReturnHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        OperationHandler actual = fruitStrategy.proceed(fruitTransaction);
        OperationHandler expected = returnOperation.handle(fruitTransaction);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void handleSupply_Ok() {
        supplyOperation = new SupplyHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        OperationHandler actual = fruitStrategy.proceed(fruitTransaction);
        OperationHandler expected = supplyOperation.handle(fruitTransaction);
        Assert.assertEquals(expected,actual);
    }
}
