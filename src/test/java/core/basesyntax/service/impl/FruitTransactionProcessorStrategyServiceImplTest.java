package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategyService;
import core.basesyntax.service.strategy.BalanceHandler;
import core.basesyntax.service.strategy.PurchaseHandler;
import core.basesyntax.service.strategy.ReturnHandler;
import core.basesyntax.service.strategy.SupplyHandler;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionProcessorStrategyServiceImplTest {
    private static OperationStrategyService operationStrategyService;
    private static StorageDao storageDao;

    @Before
    public void setUp() {
        operationStrategyService = new OperationStrategyServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void get_balanceHandler_Ok() {
        Class<?> actual = operationStrategyService
                .get(FruitTransaction.Operation.BALANCE).getClass();
        Class<?> expected = BalanceHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseHandler_Ok() {
        Class<?> actual = operationStrategyService
                .get(FruitTransaction.Operation.PURCHASE).getClass();
        Class<?> expected = PurchaseHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_supplyHandler_Ok() {
        Class<?> actual = operationStrategyService
                .get(FruitTransaction.Operation.SUPPLY).getClass();
        Class<?> expected = SupplyHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnHandler_Ok() {
        Class<?> actual = operationStrategyService
                .get(FruitTransaction.Operation.RETURN).getClass();
        Class<?> expected = ReturnHandler.class;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_null_NotOk() {
        operationStrategyService.get(null);
    }
}
