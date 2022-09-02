package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.storage.StorageImpl;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy strategy;
    private Storage storage;

    @Before
    public void setUp() {
        storage = new StorageImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandler = new HashMap<>();
        operationHandler.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storage));
        operationHandler.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storage));
        operationHandler.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storage));
        operationHandler.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storage));
        strategy = new OperationStrategyImpl(operationHandler);
    }

    @After
    public void tearDown() {
        storage.clearStorage();
    }

    @Test
    public void operationBalance_Ok() {
        final FruitTransaction b_apple_100 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
        strategy.get(b_apple_100.getOperation())
                .processingOperation(b_apple_100);
        int actualResult = storage.getEntrySet().stream()
                .map(e -> e.getValue())
                .mapToInt(value -> value).sum();
        int expectedResult = 100;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void operationReturn_Ok() {
        final FruitTransaction r_apple_5 =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 5);
        strategy.get(r_apple_5.getOperation())
                .processingOperation(r_apple_5);
        int actualResult = storage.getEntrySet().stream()
                .map(e -> e.getValue())
                .mapToInt(value -> value).sum();
        int expectedResult = 5;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void operationSupply_Ok() {
        final FruitTransaction s_apple_50 =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50);
        strategy.get(s_apple_50.getOperation())
                .processingOperation(s_apple_50);
        int actualResult = storage.getEntrySet().stream()
                .map(e -> e.getValue())
                .mapToInt(value -> value).sum();
        int expectedResult = 50;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void operationPurchaseFromEmptyStorage_NotOk() {
        final FruitTransaction b_orange_40 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 40);
        final FruitTransaction p_apple_30 =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30);
        strategy.get(p_apple_30.getOperation())
                .processingOperation(b_orange_40);
    }
}
