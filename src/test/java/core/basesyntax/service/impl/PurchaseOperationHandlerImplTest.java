package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static StorageDao storageDao;
    private static FruitService fruitService;

    @Before
    public void init() {
        storageDao = new StorageDaoImpl();
        fruitService = new FruitServiceImpl(storageDao);
    }

    @Test
    public void handlePurchase_Ok() {
        String fruitName = "banana";
        storageDao.update(fruitName, 100);
        FruitOperation operation = new FruitOperation();
        operation.setOperation(FruitOperation.Operation.PURCHASE);
        operation.setAmount(60);
        operation.setFruit(fruitName);
        Map<FruitOperation.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitOperation.Operation.PURCHASE,
                new PurchaseOperationHandlerImpl(fruitService));
        Strategy strategy = new StrategyImpl(operationHandlerMap);
        strategy.get(operation.getOperation()).handle(operation);
        int actual = Storage.fruitsStorage.get(fruitName);
        assertEquals(40, actual);
    }

    @After
    public void after() {
        Storage.fruitsStorage.clear();
    }
}
