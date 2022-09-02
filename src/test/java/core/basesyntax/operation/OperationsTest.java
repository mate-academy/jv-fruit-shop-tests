package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OperationsTest {
    private static final FruitTransaction BALANCE_OPERATION_500_BANANA
            = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 500);
    private static final FruitTransaction BALANCE_OPERATION_220_APPLE
            = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 220);
    private static final FruitTransaction PURCHASE_OPERATION_150_BANANA
            = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 150);
    private static final FruitTransaction PURCHASE_OPERATION_50_APPLE
            = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50);
    private static final FruitTransaction RETURN_OPERATION_500_BANANA
            = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 500);
    private static final FruitTransaction SUPPLY_OPERATION_250_BANANA
            = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 250);

    private FruitDao fruitDao;
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> activityOperationMap = new HashMap<>();
        activityOperationMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitDao));
        activityOperationMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao));
        activityOperationMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitDao));
        activityOperationMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(activityOperationMap);
    }

    @After
    public void afterSetUp() {
        Storage.fruits.clear();
    }

    @Test
    public void balanceOperation_Ok() {
        operationStrategy.get(BALANCE_OPERATION_500_BANANA.getOperation())
                .handle(BALANCE_OPERATION_500_BANANA);
        operationStrategy.get(BALANCE_OPERATION_220_APPLE.getOperation())
                .handle(BALANCE_OPERATION_220_APPLE);
        Integer expectedBanana = 500;
        Integer expectedApple = 220;
        Integer actualBanana = fruitDao.getEntries().stream()
                .filter(a -> a.getKey().equals("banana")).findFirst().get().getValue();
        Integer actualApple = fruitDao.getEntries().stream()
                .filter(a -> a.getKey().equals("apple")).findFirst().get().getValue();
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedApple, actualApple);
    }

    @Test
    public void purchaseOperation_ok() {
        operationStrategy.get(BALANCE_OPERATION_500_BANANA.getOperation())
                .handle(BALANCE_OPERATION_500_BANANA);
        operationStrategy.get(BALANCE_OPERATION_220_APPLE.getOperation())
                .handle(BALANCE_OPERATION_220_APPLE);
        operationStrategy.get(PURCHASE_OPERATION_150_BANANA.getOperation())
                .handle(PURCHASE_OPERATION_150_BANANA);
        operationStrategy.get(PURCHASE_OPERATION_50_APPLE.getOperation())
                .handle(PURCHASE_OPERATION_50_APPLE);
        Integer expectedBanana = 350;
        Integer expectedApple = 170;
        Integer actualBanana = fruitDao.getEntries().stream()
                .filter(a -> a.getKey().equals("banana")).findFirst().get().getValue();
        Integer actualApple = fruitDao.getEntries().stream()
                .filter(a -> a.getKey().equals("apple")).findFirst().get().getValue();
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedApple, actualApple);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_notOk() {
        operationStrategy.get(PURCHASE_OPERATION_150_BANANA.getOperation())
                .handle(PURCHASE_OPERATION_150_BANANA);
        operationStrategy.get(PURCHASE_OPERATION_50_APPLE.getOperation())
                .handle(PURCHASE_OPERATION_50_APPLE);
    }

    @Test
    public void return_Ok() {
        operationStrategy.get(BALANCE_OPERATION_500_BANANA.getOperation())
                .handle(BALANCE_OPERATION_500_BANANA);
        operationStrategy.get(RETURN_OPERATION_500_BANANA.getOperation())
                .handle(RETURN_OPERATION_500_BANANA);
        Integer expected = 1000;
        Integer actual = fruitDao.getEntries().stream()
                .filter(a -> a.getKey().equals("banana")).findFirst().get().getValue();
        assertEquals(expected, actual);
    }

    @Test
    public void supply_Ok() {
        operationStrategy.get(BALANCE_OPERATION_500_BANANA.getOperation())
                .handle(BALANCE_OPERATION_500_BANANA);
        operationStrategy.get(SUPPLY_OPERATION_250_BANANA.getOperation())
                .handle(SUPPLY_OPERATION_250_BANANA);
        Integer expected = 750;
        Integer actual = fruitDao.getEntries().stream()
                .filter(a -> a.getKey().equals("banana")).findFirst().get().getValue();
        assertEquals(expected, actual);
    }
}
