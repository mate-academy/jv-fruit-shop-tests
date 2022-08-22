package serviceimpl;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import handler.BalanceOperationHandler;
import handler.OperationHandler;
import handler.PurchaseOperationHandler;
import handler.ReturnOperationHandler;
import handler.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.TransactionProcessor;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;

public class TransactionImplTest {
    private static TransactionProcessor transactionProcessor;
    private static Storage storage;
    private static StorageDao storageDao;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        storageDao = new StorageDaoImpl(storage);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionProcessor = new TransactionImpl(operationStrategy);
    }

    @Before
    public void setUp() {
        fruitTransactions = new ArrayList<>();
    }

    @Test
    public void process_Ok() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana",50));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 5));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 50);
        expected.put("apple", 35);
        transactionProcessor.process(fruitTransactions);
        Map<String, Integer> actual = storageDao.getAll();
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        fruitTransactions.clear();
    }
}
