package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.transaction.BalanceTransactionHandler;
import core.basesyntax.service.transaction.PurchaseTransactionHandler;
import core.basesyntax.service.transaction.ReturnTransactionHandler;
import core.basesyntax.service.transaction.SupplyTransactionHandler;
import core.basesyntax.service.transaction.TransactionHandler;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static Map<Operation, TransactionHandler> handlerMap;
    private static StorageDao storageDao;
    private static TransactionStrategy strategy;
    private static FruitShopService shopService;

    @BeforeClass
    public static void beforeClass() {
        handlerMap = new HashMap<>();
        storageDao = new StorageDaoImpl();
        TransactionHandler balanceTransactionHandler = new BalanceTransactionHandler(storageDao);
        TransactionHandler purchaseTransactionHandler = new PurchaseTransactionHandler(storageDao);
        TransactionHandler returnTransactionHandler = new ReturnTransactionHandler(storageDao);
        TransactionHandler supplyTransactionHandler = new SupplyTransactionHandler(storageDao);
        handlerMap.put(Operation.BALANCE, balanceTransactionHandler);
        handlerMap.put(Operation.PURCHASE, purchaseTransactionHandler);
        handlerMap.put(Operation.RETURN, returnTransactionHandler);
        handlerMap.put(Operation.SUPPLY, supplyTransactionHandler);
        strategy = new TransactionStrategyImpl(handlerMap);
        shopService = new FruitShopServiceImpl(strategy);
    }

    @Test(expected = RuntimeException.class)
    public void processTransactions_firstTransactionPurchase_notOk() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(Operation.PURCHASE, "banana", 30));
        transactions.add(new Transaction(Operation.BALANCE, "banana", 30));
        transactions.add(new Transaction(Operation.BALANCE, "apple", 50));
        transactions.add(new Transaction(Operation.SUPPLY, "apple", 50));
        transactions.add(new Transaction(Operation.RETURN, "banana", 40));
        transactions.add(new Transaction(Operation.PURCHASE, "apple", 50));
        shopService.processTransactions(transactions);
    }

    @Test
    public void processTransactions_validListTransaction_ok() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(Operation.BALANCE, "banana", 30));
        transactions.add(new Transaction(Operation.BALANCE, "apple", 50));
        transactions.add(new Transaction(Operation.SUPPLY, "apple", 50));
        transactions.add(new Transaction(Operation.RETURN, "banana", 40));
        transactions.add(new Transaction(Operation.PURCHASE, "banana", 30));
        transactions.add(new Transaction(Operation.PURCHASE, "apple", 50));
        shopService.processTransactions(transactions);
        Assert.assertTrue("Test failed! Storage must contain a banana",
                Storage.fruitStorage.containsKey("banana"));
        Assert.assertTrue("Test failed! Storage must contain an apple",
                Storage.fruitStorage.containsKey("apple"));
        Integer expectedBanana = 40;
        Integer expectedApple = 50;
        Integer actualBanana = Storage.fruitStorage.get("banana");
        Integer actualApple = Storage.fruitStorage.get("apple");
        Assert.assertEquals("There should be " + expectedBanana + " banana but actual is "
                + actualBanana, expectedBanana, actualBanana);
        Assert.assertEquals("There should be " + expectedApple + " apple but actual is "
                + actualApple, expectedApple, actualApple);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruitStorage.clear();
    }
}
