package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.strategy.BalanceOperationHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.PurchaseOperationHandler;
import core.basesyntax.service.strategy.ReturnOperationHandler;
import core.basesyntax.service.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static StorageDao storageDao;
    private static OperationHandler balanceHandler;
    private static OperationHandler purchaseHandler;
    private static OperationHandler returnHandler;
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        balanceHandler = new BalanceOperationHandler(storageDao);
        purchaseHandler = new PurchaseOperationHandler(storageDao);
        returnHandler = new ReturnOperationHandler(storageDao);
        supplyHandler = new SupplyOperationHandler(storageDao);
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        handlerMap.put(FruitTransaction.Operation.RETURN, returnHandler);
        handlerMap.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void get_balanceOperation_Ok() {
        Class<?> expected = balanceHandler.getClass();
        Class<?> actual = operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseOperation_Ok() {
        Class<?> expected = purchaseHandler.getClass();
        Class<?> actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnOperation_Ok() {
        Class<?> expected = returnHandler.getClass();
        Class<?> actual = operationStrategy.get(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_supplyOperation_Ok() {
        Class<?> expected = supplyHandler.getClass();
        Class<?> actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }
}
