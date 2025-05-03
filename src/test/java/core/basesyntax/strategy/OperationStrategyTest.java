package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.StorageDao;
import core.basesyntax.storage.StorageDaoImpl;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static StorageDao storageDao;

    @BeforeClass
    public static void init() {
        StorageDao storageDao = new StorageDaoImpl();
        operationStrategy = new OperationStrategyImpl(storageDao);
    }

    @Test
    public void getBalanceOperationHandler_Ok() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        Class<?> actual = operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getPurchaseOperationHandler_Ok() {
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        Class<?> actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getReturnOperationHandler_Ok() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        Class<?> actual = operationStrategy.get(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getSupplyOperationHandler_Ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        Class<?> actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }
}
