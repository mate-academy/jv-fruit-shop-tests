package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.strategy.TransactionHandler;
import core.basesyntax.strategy.TransactionHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceTransactionHandler;
import core.basesyntax.strategy.impl.PurchaseTransactionHandler;
import core.basesyntax.strategy.impl.ReturnTransactionHandler;
import core.basesyntax.strategy.impl.SupplyTransactionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static FruitStorageDao dao;
    private static Map<FruitTransaction.Operation, TransactionHandler> map;
    private static TransactionHandlerStrategy strategy;
    private static TransactionService transactionService;

    @BeforeClass
    public static void beforeClass() {
        dao = new FruitStorageDaoImpl();
        map = new HashMap<>();
        map.put(FruitTransaction.Operation.BALANCE, new BalanceTransactionHandler(dao));
        map.put(FruitTransaction.Operation.SUPPLY, new SupplyTransactionHandler(dao));
        map.put(FruitTransaction.Operation.PURCHASE, new PurchaseTransactionHandler(dao));
        map.put(FruitTransaction.Operation.RETURN, new ReturnTransactionHandler(dao));
        strategy = new TransactionHandlerStrategy(map);
        transactionService = new TransactionServiceImpl(strategy);
    }

    @Test
    public void executeTransactions_ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transactionFirst = new FruitTransaction("s", "apple", 100);
        FruitTransaction transactionSecond = new FruitTransaction("r", "banana", 20);
        transactions.add(transactionFirst);
        transactions.add(transactionSecond);
        transactionService.executeTransactions(transactions);
        Assert.assertEquals("Expected amount of apple is 100", 100,
                (int) FruitStorage.storage.get("apple"));
        Assert.assertEquals("Expected amount of banana is 20", 20,
                (int) FruitStorage.storage.get("banana"));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
