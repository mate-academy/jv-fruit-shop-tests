package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperation;
import core.basesyntax.operation.ReturnOperation;
import core.basesyntax.operation.SupplyOperation;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitShopServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        FruitShopService fruitShopService = new FruitShopServiceImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation(fruitShopService));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(fruitShopService));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation(fruitShopService));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation(fruitShopService));
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void balanceOperationHandle_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        operationStrategy.proceedTransaction(fruitTransaction);
        Assert.assertTrue(Storage.storage.containsKey("banana"));
        Assert.assertTrue(Storage.storage.containsValue(20));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @Test (expected = RuntimeException.class)
    public void purchaseOperationHandleWithNotEnoughQuantity_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        operationStrategy.proceedTransaction(fruitTransaction);
    }

    @Test
    public void returnOperationHandle_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        operationStrategy.proceedTransaction(fruitTransaction);
        Assert.assertTrue(Storage.storage.containsKey("banana"));
        Assert.assertTrue(Storage.storage.containsValue(20));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @Test
    public void supplyOperationHandle_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        operationStrategy.proceedTransaction(fruitTransaction);
        Assert.assertTrue(Storage.storage.containsKey("banana"));
        Assert.assertTrue(Storage.storage.containsValue(20));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
