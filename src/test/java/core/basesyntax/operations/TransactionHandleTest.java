package core.basesyntax.operations;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.impl.BalanceTransactionHandleImpl;
import core.basesyntax.operations.impl.PurchaseTransactionHandleImpl;
import core.basesyntax.operations.impl.ReturnTransactionHandleImpl;
import core.basesyntax.operations.impl.SupplyTransactionHandleImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionHandleTest {
    private static OperationStrategy operationStrategy;
    private static StorageDao storage;
    private static Map<FruitTransaction.Operation, TransactionHandle> operationHandler
            = new HashMap<>();

    private static final FruitTransaction B_BANANA_20 =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
    private static final FruitTransaction R_BANANA_90 =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 90);
    private static final FruitTransaction S_APPLE_100 =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100);
    private static final FruitTransaction P_APPLE_50 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50);

    @BeforeClass
    public static void beforeClass() {
        storage = new StorageDaoImpl();
        operationHandler.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandleImpl(storage));
        operationHandler.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandleImpl(storage));
        operationHandler.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandleImpl(storage));
        operationHandler.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandleImpl(storage));

        operationStrategy = new OperationStrategyImpl(operationHandler);
    }

    @After
    public void afterClass() {
        Storage.storage.clear();
    }

    @Test
    public void transaction_balance_ok() {
        operationStrategy.getByOperation(B_BANANA_20.getOperation())
                .executeTransaction(B_BANANA_20);
        int actual = sumValueOf(storage);
        int expected = 20;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transaction_return_ok() {
        operationStrategy.getByOperation(R_BANANA_90.getOperation())
                .executeTransaction(R_BANANA_90);
        int actual = sumValueOf(storage);
        int expected = 90;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transaction_supply_ok() {
        operationStrategy.getByOperation(S_APPLE_100.getOperation())
                .executeTransaction(S_APPLE_100);
        int actual = sumValueOf(storage);
        int expected = 100;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void transaction_purchase_ok() {
        operationStrategy.getByOperation(P_APPLE_50.getOperation())
                .executeTransaction(P_APPLE_50);
        int actual = sumValueOf(storage);
        int expected = 50;
        Assert.assertEquals(expected, actual);
    }

    private int sumValueOf(StorageDao storage) {
        return storage.getEntries().stream()
                .map(v -> v.getValue())
                .mapToInt(v -> v)
                .sum();
    }
}
