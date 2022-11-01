package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageDao;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.transactions.FruitTransaction;
import core.basesyntax.strategy.transactions.TransactionHandler;
import core.basesyntax.strategy.transactions.impl.AdderHandler;
import core.basesyntax.strategy.transactions.impl.ReduceHandler;
import core.basesyntax.strategy.transactions.impl.SaverHandler;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceTest {
    private static TransactionService transactionService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeAll() {
        storageDao = new StorageDao();
        transactionService = new TransactionServiceImpl(getTransactionMap(storageDao));
    }

    @After
    public void afterEach() {
        Storage.getFruitStorage().clear();
    }

    @Test
    public void transaction_applyPurchaseLessZeroPlus_Ok() {
        storageDao.add("banana", 10);
        transactionService.applyTransactions(List.of(
                getFruitTransaction(Operation.PURCHASE, "banana", -10)
        ));
        Integer expected = 20;
        Integer actual = storageDao.getValue("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transaction_applyBalanceLessZero_ok() {
        storageDao.add("banana", 0);
        transactionService.applyTransactions(List.of(
                getFruitTransaction(Operation.PURCHASE, "banana", 10)
        ));
        Integer expected = -10;
        Integer actual = storageDao.getValue("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transaction_applyBalanceChangeValue_ok() {
        storageDao.add("banana", 20);
        transactionService.applyTransactions(List.of(
                getFruitTransaction(Operation.BALANCE, "banana", 10)
        ));
        Integer expected = 10;
        Integer actual = storageDao.getValue("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transaction_applyCorrectMultipleTransactions_ok() {
        transactionService.applyTransactions(List.of(
                getFruitTransaction(Operation.BALANCE, "apple", 10),
                getFruitTransaction(Operation.BALANCE, "banana", 20),
                getFruitTransaction(Operation.SUPPLY, "apple", 10),
                getFruitTransaction(Operation.SUPPLY, "banana", 5),
                getFruitTransaction(Operation.PURCHASE, "apple", 10),
                getFruitTransaction(Operation.PURCHASE, "banana", 5),
                getFruitTransaction(Operation.RETURN, "banana", 2)
        ));
        Integer expectedBanana = 22;
        Integer actualBanana = storageDao.getValue("banana");
        Integer expectedApple = 10;
        Integer actualApple = storageDao.getValue("apple");
        Assert.assertEquals(expectedBanana, actualBanana);
        Assert.assertEquals(expectedApple, actualApple);
    }

    @Test
    public void transaction_applyCorrectTransactionSupply_ok() {
        storageDao.add("apple", 10);
        transactionService.applyTransactions(List.of(
                getFruitTransaction(Operation.SUPPLY, "apple", 10)
        ));
        Integer expected = 20;
        Integer actual = storageDao.getValue("apple");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transaction_applyCorrectTransactionReturn_ok() {
        storageDao.add("apple", 50);
        transactionService.applyTransactions(List.of(
                getFruitTransaction(Operation.RETURN, "apple", 10)
        ));
        Integer expected = 60;
        Integer actual = storageDao.getValue("apple");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transaction_applyCorrectTransactionPurchase_ok() {
        storageDao.add("banana", 50);
        transactionService.applyTransactions(List.of(
                getFruitTransaction(Operation.PURCHASE, "banana", 20)));
        Integer expected = 30;
        Integer actual = storageDao.getValue("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transaction_applyCorrectTransactionBalance_ok() {
        transactionService.applyTransactions(List.of(
                getFruitTransaction(Operation.BALANCE, "milk", 10)
        ));
        Integer expected = 10;
        Integer actual = storageDao.getValue("milk");
        Assert.assertEquals(expected, actual);
    }

    private static FruitTransaction getFruitTransaction(Operation operation,
                                                        String name, int value) {
        return new FruitTransaction()
                .setOperation(operation)
                .setFruitName(name)
                .setValueOfFruit(value);
    }

    private static HashMap<Operation, TransactionHandler> getTransactionMap(StorageDao storageDao) {
        HashMap<Operation, TransactionHandler> transactionMap = new HashMap<>();
        transactionMap.put(Operation.BALANCE, new SaverHandler(storageDao));
        transactionMap.put(Operation.PURCHASE, new ReduceHandler(storageDao));
        transactionMap.put(Operation.SUPPLY, new AdderHandler(storageDao));
        transactionMap.put(Operation.RETURN, new AdderHandler(storageDao));
        return transactionMap;
    }
}
