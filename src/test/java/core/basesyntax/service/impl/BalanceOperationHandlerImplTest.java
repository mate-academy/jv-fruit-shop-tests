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
import org.junit.Test;

public class BalanceOperationHandlerImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private FruitService fruitService = new FruitServiceImpl(storageDao);

    @Test
    public void handleBalance_Ok() {
        String fruitName = "banana";
        FruitOperation transaction = new FruitOperation();
        transaction.setOperation(FruitOperation.Operation.BALANCE);
        transaction.setAmount(200);
        transaction.setFruit(fruitName);
        Map<FruitOperation.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitOperation.Operation.BALANCE,
                new BalanceOperationHandlerImpl(fruitService));
        operationHandlerMap.put(FruitOperation.Operation.SUPPLY,
                new SupplyOperationHandlerImpl(fruitService));
        operationHandlerMap.put(FruitOperation.Operation.PURCHASE,
                new PurchaseOperationHandlerImpl(fruitService));
        operationHandlerMap.put(FruitOperation.Operation.RETURN,
                new ReturnOperationHandlerImpl(fruitService));
        Strategy strategy = new StrategyImpl(operationHandlerMap);
        strategy.get(transaction.getOperation()).handle(transaction);
        int actual = Storage.fruitsStorage.get(fruitName);
        assertEquals(200, actual);
    }

    @After
    public void after() {
        Storage.fruitsStorage.clear();
    }
}
