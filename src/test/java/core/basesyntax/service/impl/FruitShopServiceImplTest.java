package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
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
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void processTransactions_firstTransactionPurchase_ok() {
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
    }
}