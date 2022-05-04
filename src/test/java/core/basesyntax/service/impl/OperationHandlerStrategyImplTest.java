package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerStrategy;
import core.basesyntax.service.strategy.BalanceOperationHandler;
import core.basesyntax.service.strategy.PurchaseOperationHandler;
import core.basesyntax.service.strategy.ReturnOperationHandler;
import core.basesyntax.service.strategy.SupplyOperationHandler;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerStrategyImplTest {
    private static OperationHandlerStrategy operationHandlerStrategy;
    private static StorageDao storageDao;

    @Before
    public void setUp() {
        operationHandlerStrategy = new OperationHandlerStrategyImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void get_BalanceHandler_Ok() {
        Class<?> actual = operationHandlerStrategy
                .get(FruitTransaction.Operation.BALANCE).getClass();
        Class<?> expected = BalanceOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_PurchaseHandler_Ok() {
        Class<?> actual = operationHandlerStrategy
                .get(FruitTransaction.Operation.PURCHASE).getClass();
        Class<?> expected = PurchaseOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_SupplyHandler_Ok() {
        Class<?> actual = operationHandlerStrategy
                .get(FruitTransaction.Operation.SUPPLY).getClass();
        Class<?> expected = SupplyOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_ReturnHandler_Ok() {
        Class<?> actual = operationHandlerStrategy
                .get(FruitTransaction.Operation.RETURN).getClass();
        Class<?> expected = ReturnOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_Null_NotOk() {
        operationHandlerStrategy.get(null);

    }
}
