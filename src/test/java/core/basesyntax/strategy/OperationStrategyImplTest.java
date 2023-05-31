package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.TransactionHandle;
import core.basesyntax.operations.impl.BalanceTransactionHandleImpl;
import core.basesyntax.operations.impl.PurchaseTransactionHandleImpl;
import core.basesyntax.operations.impl.ReturnTransactionHandleImpl;
import core.basesyntax.operations.impl.SupplyTransactionHandleImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static StorageDao storage;
    private static Map<FruitTransaction.Operation, TransactionHandle> operationHandler
            = new HashMap<>();

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
    public void after() {
        Storage.storage.clear();
    }

    @Test
    public void strategy_executeBalanceTransaction_ok() {
        Class actual = new OperationStrategyImpl(operationHandler)
                .getByOperation(FruitTransaction.Operation.BALANCE)
                .getClass();
        Class expected = BalanceTransactionHandleImpl.class;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void strategy_executeReturnTransaction_ok() {
        Class actual = new OperationStrategyImpl(operationHandler)
                .getByOperation(FruitTransaction.Operation.RETURN)
                .getClass();
        Class expected = ReturnTransactionHandleImpl.class;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void strategy_executePurchaseTransaction_ok() {
        Class actual = new OperationStrategyImpl(operationHandler)
                .getByOperation(FruitTransaction.Operation.PURCHASE)
                .getClass();
        Class expected = PurchaseTransactionHandleImpl.class;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void strategy_executeSupplyTransaction_ok() {
        Class actual = new OperationStrategyImpl(operationHandler)
                .getByOperation(FruitTransaction.Operation.SUPPLY)
                .getClass();
        Class expected = SupplyTransactionHandleImpl.class;
        Assert.assertEquals(expected, actual);
    }
}
