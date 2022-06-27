package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitShopTransactions;
import core.basesyntax.service.OperationService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceImplTest {
    private static OperationService operationService;
    private static List<FruitShopTransactions> fruitShopTransactions;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        fruitShopTransactions = new ArrayList<>();
        storageDao = new StorageDaoImpl();
        Map<FruitShopTransactions.Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitShopTransactions.Operation.BALANCE, new BalanceHandler(storageDao));
        strategyMap.put(FruitShopTransactions.Operation.SUPPLY, new SupplyHandler(storageDao));
        strategyMap.put(FruitShopTransactions.Operation.PURCHASE, new PurchaseHandler(storageDao));
        strategyMap.put(FruitShopTransactions.Operation.RETURN, new ReturnHandler(storageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(strategyMap);
        operationService = new OperationServiceImpl(operationStrategy);
    }

    @Test(expected = RuntimeException.class)
    public void processData_negativeBalance_notOk() {
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.BALANCE, "apple", -1));
        operationService.processData(fruitShopTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processData_negativePurchase_notOk() {
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.PURCHASE, "apple", -1));
        operationService.processData(fruitShopTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processData_toBigPurchase_notOk() {
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.BALANCE, "apple", 50));
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.PURCHASE, "apple", 80));
        operationService.processData(fruitShopTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processData_negativeReturn_notOk() {
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.RETURN, "banana", -1));
        operationService.processData(fruitShopTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processData_negativeSupply_notOk() {
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.SUPPLY, "banana", -1));
        operationService.processData(fruitShopTransactions);
    }

    @Test
    public void processData_balance_ok() {
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.BALANCE, "orange", 5));
        operationService.processData(fruitShopTransactions);
        int expected = 5;
        int actual = storageDao.getQuantity("orange").orElse(0);
        assertEquals(expected, actual);
    }

    @Test
    public void processData_supply_ok() {
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.SUPPLY, "lemon", 10));
        operationService.processData(fruitShopTransactions);
        int expected = 10;
        int actual = storageDao.getQuantity("lemon").orElse(0);
        assertEquals(expected, actual);
    }

    @Test
    public void processData_purchase_ok() {
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.BALANCE, "cherry", 50));
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.PURCHASE, "cherry", 25));
        operationService.processData(fruitShopTransactions);
        int expected = 25;
        int actual = storageDao.getQuantity("cherry").orElse(0);
        assertEquals(expected, actual);
    }

    @Test
    public void processData_return_ok() {
        fruitShopTransactions.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.RETURN, "plum", 5));
        operationService.processData(fruitShopTransactions);
        int expected = 5;
        int actual = storageDao.getQuantity("plum").orElse(0);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
        fruitShopTransactions.clear();
    }
}
