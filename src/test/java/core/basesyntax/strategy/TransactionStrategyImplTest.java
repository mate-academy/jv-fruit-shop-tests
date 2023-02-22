package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.transaction.BalanceTransactionHandler;
import core.basesyntax.service.transaction.PurchaseTransactionHandler;
import core.basesyntax.service.transaction.ReturnTransactionHandler;
import core.basesyntax.service.transaction.SupplyTransactionHandler;
import core.basesyntax.service.transaction.TransactionHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TransactionStrategyImplTest {

    private static Map<Operation, TransactionHandler> handlerMap;
    private static StorageDao storageDao;
    private static TransactionStrategy transactionStrategy;
    
    @BeforeClass
    public static void beforeClass() {
        handlerMap = new HashMap<>();
        transactionStrategy = new TransactionStrategyImpl(handlerMap);
        storageDao = new StorageDaoImpl();
        TransactionHandler balanceTransactionHandler = new BalanceTransactionHandler(storageDao);
        TransactionHandler purchaseTransactionHandler = new PurchaseTransactionHandler(storageDao);
        TransactionHandler returnTransactionHandler = new ReturnTransactionHandler(storageDao);
        TransactionHandler supplyTransactionHandler = new SupplyTransactionHandler(storageDao);
        handlerMap.put(Operation.BALANCE, balanceTransactionHandler);
        handlerMap.put(Operation.PURCHASE, purchaseTransactionHandler);
        handlerMap.put(Operation.RETURN, returnTransactionHandler);
        handlerMap.put(Operation.SUPPLY, supplyTransactionHandler);
    }

    @Test
    public void handleOperation_successBalance_ok() {
        Integer expectedValue = 20;
        Transaction transaction = new Transaction(Operation.BALANCE, "banana", 20);
        transactionStrategy.handleOperation(transaction);
        Integer actualValue = Storage.fruitStorage.get("banana");
        Assert.assertTrue("Test failed! Storage should has banana", Storage.fruitStorage.containsKey("banana"));
        Assert.assertEquals("There are " + actualValue + " numbers of "
                + transaction.getFruitName() + "but should be " + expectedValue, expectedValue, actualValue);
    }

    @Test(expected = RuntimeException.class)
    public void handleOperation_EmptyStoragePurchase_notOk() {
        Transaction transaction = new Transaction(Operation.PURCHASE, "banana", 20);
        transactionStrategy.handleOperation(transaction);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}