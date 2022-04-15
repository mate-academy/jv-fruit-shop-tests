package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.BalanceOperationHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.PurchaseOperationHandler;
import core.basesyntax.service.strategy.ReturnOperationHandler;
import core.basesyntax.service.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
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
    public void get_BalanceOperation_Ok() {
        OperationHandler expected = balanceHandler;
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expected, actual);
    }

    @Test
    public void get_PurchaseOperation_Ok() {
        OperationHandler expected = purchaseHandler;
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(expected, actual);
    }

    @Test
    public void get_ReturnOperation_Ok() {
        OperationHandler expected = returnHandler;
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(expected, actual);
    }

    @Test
    public void get_SupplyOperation_Ok() {
        OperationHandler expected = supplyHandler;
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(expected, actual);
    }
}
