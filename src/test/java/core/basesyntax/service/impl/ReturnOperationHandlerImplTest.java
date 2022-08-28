package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ReturnOperationHandlerImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private FruitService fruitService = new FruitServiceImpl(storageDao);

    @Test
    public void handleReturn_Ok() {
        String fruitName = "banana";
        storageDao.update(fruitName, 35);
        FruitOperation operation = new FruitOperation();
        operation.setOperation(FruitOperation.Operation.RETURN);
        operation.setAmount(15);
        operation.setFruit(fruitName);
        Map<FruitOperation.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitOperation.Operation.BALANCE, new BalanceOperationHandlerImpl(fruitService));
        operationHandlerMap.put(FruitOperation.Operation.SUPPLY, new SupplyOperationHandlerImpl(fruitService));
        operationHandlerMap.put(FruitOperation.Operation.PURCHASE, new PurchaseOperationHandlerImpl(fruitService));
        operationHandlerMap.put(FruitOperation.Operation.RETURN, new ReturnOperationHandlerImpl(fruitService));
        Strategy strategy = new StrategyImpl(operationHandlerMap);
        strategy.get(operation.getOperation()).handle(operation);
        int actual = Storage.fruitsStorage.get(operation.getFruit());
        assertEquals(50, actual);
    }

    @After
    public void after() {
        Storage.fruitsStorage.clear();
    }
}
