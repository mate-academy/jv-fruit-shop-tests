package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.impl.BalanceHandler;
import core.basesyntax.handler.impl.PurchaseHandler;
import core.basesyntax.handler.impl.ReturnHandler;
import core.basesyntax.handler.impl.SupplyHandler;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.servises.FruitService;
import core.basesyntax.servises.impl.FruitServiceImpl;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.impl.StrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServicesWrongTests {
    private static FruitService fruitService;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static List<FruitTransaction> fruitTransactionList;
    private static StorageDao dao;

    @BeforeClass
    public static void setUp() {
        fruitTransactionList = new ArrayList<>();
        dao = new StorageDaoImpl();
        operationHandlerMap = new HashMap<>();
        Strategy strategy = new StrategyImpl(operationHandlerMap);
        fruitService = new FruitServiceImpl(strategy);
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(dao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler(dao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(dao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(dao));
    }

    @Test(expected = RuntimeException.class)
    public void fruitService_purchaseAbsentFruit_notOk() {
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "carrot", 10));
        fruitService.applyTransaction(fruitTransactionList);
    }

    @Test(expected = RuntimeException.class)
    public void fruitService_returnAbsentFruit_notOk() {
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "melon", 10));
        fruitService.applyTransaction(fruitTransactionList);
    }

    @Test(expected = RuntimeException.class)
    public void fruitService_supplyAbsentFruit_notOk() {
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "watermelon", 10));
        fruitService.applyTransaction(fruitTransactionList);
    }

    @Test (expected = RuntimeException.class)
    public void fruitService_purchaseTooMachFruits_notOk() {
        dao.addToStorage("orange", 15);
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "orange", 35));
        fruitService.applyTransaction(fruitTransactionList);
    }

    @After
    public void clean() {
        Storage.storage.clear();
    }
}
