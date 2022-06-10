package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerStrategy;
import core.basesyntax.service.OperationProcessor;
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

public class OperationProcessorImplTest {
    private static OperationProcessor operationProcessor;
    private static List<FruitTransaction> fruitTransactions;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactions = new ArrayList<>();
        storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(storageDao));
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(storageDao));
        strategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(storageDao));
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler(storageDao));
        OperationHandlerStrategy operationHandlerStrategy =
                new OperationHandlerStrategyImpl(strategyMap);
        operationProcessor =
                new OperationProcessorImpl(operationHandlerStrategy);
    }

    @Test(expected = RuntimeException.class)
    public void setNegativeBalance_notOk() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "pear", -10));
        operationProcessor.processData(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void setNegativeSupply_notOk() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "lemon", -3));
        operationProcessor.processData(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void setToBigPurchase_notOk() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "pear", 10));
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "pear", 20));
        operationProcessor.processData(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void setNegativeReturn_notOk() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, "lemon", -3));
        operationProcessor.processData(fruitTransactions);
    }

    @Test
    public void setBalance_ok() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "pear", 10));
        operationProcessor.processData(fruitTransactions);
        int expected = 10;
        int actual = storageDao.getRemainingFruits("pear");
        assertEquals(expected, actual);
    }

    @Test
    public void setSupply_ok() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "lemon", 15));
        operationProcessor.processData(fruitTransactions);
        int expected = 15;
        int actual = storageDao.getRemainingFruits("lemon");
        assertEquals(expected, actual);
    }

    @Test
    public void setPurchase_ok() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "pear", 40));
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "pear", 20));
        operationProcessor.processData(fruitTransactions);
        int expected = 20;
        int actual = storageDao.getRemainingFruits("pear");
        assertEquals(expected, actual);
    }

    @Test
    public void setReturn_ok() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, "lemon", 3));
        operationProcessor.processData(fruitTransactions);
        int expected = 3;
        int actual = storageDao.getRemainingFruits("lemon");
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
        fruitTransactions.clear();
    }
}
