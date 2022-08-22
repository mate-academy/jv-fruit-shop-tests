package core.basesyntax.service.fileoperation.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.fileoperation.TransactionProcessing;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.impl.BalanceHandler;
import core.basesyntax.service.operation.impl.PurchaseHandler;
import core.basesyntax.service.operation.impl.ReturnHandler;
import core.basesyntax.service.operation.impl.SupplyHandler;
import core.basesyntax.strategy.StrategyOperationImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessingImplTest {
    private static TransactionProcessing transactionProcessor;

    @BeforeClass
    public static void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        Map<Transaction.Type, OperationHandler> typeOperationMap = new HashMap<>();
        typeOperationMap.put(Transaction.Type.BALANCE, new BalanceHandler(storageDao));
        typeOperationMap.put(Transaction.Type.SUPPLY, new SupplyHandler(storageDao));
        typeOperationMap.put(Transaction.Type.PURCHASE, new PurchaseHandler(storageDao));
        typeOperationMap.put(Transaction.Type.RETURN, new ReturnHandler(storageDao));
        transactionProcessor = new TransactionProcessingImpl(
                new StrategyOperationImpl(typeOperationMap));
    }

    @After
    public void clear_storage() {
        FruitShopStorage.storageFruits.clear();
    }

    @Test
    public void add_transaction_Ok() {
        Transaction balanceBanana = new Transaction(new Fruit("banana", 100),
                Transaction.Type.getTypeOperation("b"));
        Transaction supplyBanana = new Transaction(new Fruit("banana", 50),
                Transaction.Type.getTypeOperation("s"));
        Transaction retryBanana = new Transaction(new Fruit("banana", 100),
                Transaction.Type.getTypeOperation("r"));
        Transaction balanceOrange = new Transaction(new Fruit("orange", 50),
                Transaction.Type.getTypeOperation("b"));
        List<Transaction> testListTransaction = new ArrayList<>();
        testListTransaction.add(balanceBanana);
        testListTransaction.add(supplyBanana);
        testListTransaction.add(retryBanana);
        testListTransaction.add(balanceOrange);
        transactionProcessor.transactionProcessing(testListTransaction);
        Fruit expectedBanana = new Fruit("banana", 250);
        Fruit actualBanana = FruitShopStorage.storageFruits.get(0);
        Fruit expectedOrange = new Fruit("orange", 50);
        Fruit actualOrange = FruitShopStorage.storageFruits.get(1);
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedOrange, actualOrange);
    }

    @Test
    public void purchase_transaction_Ok() {
        Transaction balanceBanana = new Transaction(new Fruit("banana", 100),
                Transaction.Type.getTypeOperation("b"));
        Transaction purchaseBanana = new Transaction(new Fruit("banana", 50),
                Transaction.Type.getTypeOperation("p"));
        List<Transaction> testListTransaction = new ArrayList<>();
        testListTransaction.add(balanceBanana);
        testListTransaction.add(purchaseBanana);
        transactionProcessor.transactionProcessing(testListTransaction);
        Fruit expectedFruit = new Fruit("banana", 50);
        assertEquals(FruitShopStorage.storageFruits.get(0), expectedFruit);
    }
}
