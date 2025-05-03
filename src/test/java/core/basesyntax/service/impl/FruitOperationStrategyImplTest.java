package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitOperationStrategy;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.FruitsOperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitOperationStrategyImplTest {
    private static FruitOperationStrategy fruitOperationStrategy;
    private static StorageDao storageDao;
    private static Map<FruitTransaction.Operation, FruitsOperationHandler> handlerMap;

    @BeforeClass
    public static void beforeAllTestMethods() {
        storageDao = new StorageDaoImpl();
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        handlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));
        handlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        fruitOperationStrategy = new FruitOperationStrategyImpl(handlerMap);
    }

    @Test
    public void get_balanceHandler_ok() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        Class<? extends FruitsOperationHandler> actual =
                fruitOperationStrategy.get(FruitTransaction.Operation.BALANCE).getClass();

        assertEquals(expected,actual);
    }

    @Test
    public void get_purchaseHandler_ok() {
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        Class<? extends FruitsOperationHandler> actual =
                fruitOperationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass();

        assertEquals(expected,actual);
    }

    @Test
    public void get_returnHandler_ok() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        Class<? extends FruitsOperationHandler> actual =
                fruitOperationStrategy.get(FruitTransaction.Operation.RETURN).getClass();

        assertEquals(expected,actual);
    }

    @Test
    public void get_supplyHandler_ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        Class<? extends FruitsOperationHandler> actual =
                fruitOperationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass();

        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_null_notOk() {
        Class<? extends FruitsOperationHandler> actual =
                fruitOperationStrategy.get(null).getClass();
    }
}
