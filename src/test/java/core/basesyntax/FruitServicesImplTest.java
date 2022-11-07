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
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServicesImplTest {
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

    @Test
    public void fruitService_balanceTest_ok() {
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 15));
        fruitService.applyTransaction(fruitTransactionList);
        Integer amountInStorage = dao.getFromStorage("banana");
        Integer expect = 15;
        Assert.assertEquals(expect, amountInStorage);
    }

    @Test
    public void fruitService_returnTest_ok() {
        Storage.storage.put("kiwi", 15);
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "kiwi", 70));
        fruitService.applyTransaction(fruitTransactionList);
        Integer except = 85;
        Integer amountInStorage = dao.getFromStorage("kiwi");
        Assert.assertEquals(except, amountInStorage);
    }

    @Test
    public void fruitService_supplyTest_ok() {
        Storage.storage.put("coconut", 15);
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "coconut", 20));
        fruitService.applyTransaction(fruitTransactionList);
        Integer except = 35;
        Integer amountInStorage = dao.getFromStorage("coconut");
        Assert.assertEquals(except, amountInStorage);
    }

    @Test
    public void fruitService_purchaseTest_ok() {
        Storage.storage.put("pear", 100);
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "pear", 20));
        fruitService.applyTransaction(fruitTransactionList);
        Integer except = 80;
        Integer amountInStorage = dao.getFromStorage("pear");
        Assert.assertEquals(except, amountInStorage);
    }

    @Test
    public void dao_getAllTest_ok() {
        HashMap<String, Integer> all = dao.getAll();
        Assert.assertEquals(all, Storage.storage);
    }
}
