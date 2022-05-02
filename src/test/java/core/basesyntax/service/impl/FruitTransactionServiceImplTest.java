package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static FruitTransactionService fruitService;
    private static OperationStrategy operationStrategy;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceOperationHandler(storageDao));
        operationHandlerMap.put("p", new PurchaseOperationHandler(storageDao));
        operationHandlerMap.put("r", new ReturnOperationHandler(storageDao));
        operationHandlerMap.put("s", new SupplyOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitService = new FruitTransactionServiceImpl(operationStrategy);
    }

    @Test(expected = RuntimeException.class)
    public void transfer_nullValue_NotOk() {
        fruitService.transfer(null);
    }

    @Test(expected = RuntimeException.class)
    public void transfer_invalidListOfFruitTransactions_NotOk() {
        Fruit orange = new Fruit("orange");
        List<FruitTransaction> transactionList = List.of(
                new FruitTransaction(orange, 0, "r"));
        fruitService.transfer(transactionList);
    }

    @Test
    public void transfer_correctList_Ok() {
        Fruit orange = new Fruit("orange");
        List<FruitTransaction> transactionList = List.of(
                new FruitTransaction(orange, 0, "b"),
                new FruitTransaction(orange, 2, "s"),
                new FruitTransaction(orange, 2, "p"));
        fruitService.transfer(transactionList);
        Assert.assertEquals(0, storageDao.get(orange).intValue());
    }

    @After
    public void cleanUp() {
        storageDao.clear();
    }
}
