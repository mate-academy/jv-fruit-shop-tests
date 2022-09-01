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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final FruitTransaction B_APPLE_100 =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
    private static final FruitTransaction S_APPLE_50 =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50);
    private static final FruitTransaction R_APPLE_5 =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 5);
    private static final FruitTransaction P_APPLE_30 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30);
    private static final FruitTransaction B_ORANGE_40 =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 40);

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

    @Test
    public void operationBalance_Ok() {
        strategy.get(B_APPLE_100.getOperation())
                .processingOperation(B_APPLE_100);
        int actualResult = storage.getEntrySet().stream()
                .map(e -> e.getValue())
                .mapToInt(value -> value).sum();
        int expectedResult = 100;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void operationReturn_Ok() {
        strategy.get(R_APPLE_5.getOperation())
                .processingOperation(R_APPLE_5);
        int actualResult = storage.getEntrySet().stream()
                .map(e -> e.getValue())
                .mapToInt(value -> value).sum();
        int expectedResult = 5;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void operationSupply_Ok() {
        strategy.get(S_APPLE_50.getOperation())
                .processingOperation(S_APPLE_50);
        int actualResult = storage.getEntrySet().stream()
                .map(e -> e.getValue())
                .mapToInt(value -> value).sum();
        int expectedResult = 50;
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void operationPurchaseFromEmptyStorage_NotOk() {
        strategy.get(P_APPLE_30.getOperation())
                .processingOperation(B_ORANGE_40);
    }
}
