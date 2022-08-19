package core.basesyntax.service.fileoperation.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import org.junit.Before;
import org.junit.Test;

public class TransactionProcessingImplTest {
    private static TransactionProcessing processing;
    private static final StorageDao dao = new StorageDaoImpl();
    private static final Transaction balanceBanana = new Transaction(new Fruit("banana", 100),
            Transaction.Type.getTypeOperation("b"));
    private static final Transaction supplyBanana = new Transaction(new Fruit("banana", 50),
            Transaction.Type.getTypeOperation("s"));
    private static final Transaction retryBanana = new Transaction(new Fruit("banana", 100),
            Transaction.Type.getTypeOperation("r"));
    private static final Transaction purchaseBanana = new Transaction(new Fruit("banana", 50),
            Transaction.Type.getTypeOperation("p"));
    private static final Transaction balanceOrange = new Transaction(new Fruit("orange", 50),
            Transaction.Type.getTypeOperation("b"));

    @Before
    public void setUp() {
        Map<Transaction.Type, OperationHandler> typeOperationMap = new HashMap<>();
        typeOperationMap.put(Transaction.Type.BALANCE, new BalanceHandler(dao));
        typeOperationMap.put(Transaction.Type.SUPPLY, new SupplyHandler(dao));
        typeOperationMap.put(Transaction.Type.PURCHASE, new PurchaseHandler(dao));
        typeOperationMap.put(Transaction.Type.RETURN, new ReturnHandler(dao));
        processing = new TransactionProcessingImpl(new StrategyOperationImpl(typeOperationMap));
    }

    @After
    public void tearDown() {
        FruitShopStorage.storageFruits.clear();
    }

    @Test
    public void add_transaction_Ok() {
        List<Transaction> testListTransaction = new ArrayList<>();
        testListTransaction.add(balanceBanana);
        testListTransaction.add(supplyBanana);
        testListTransaction.add(retryBanana);
        testListTransaction.add(balanceOrange);
        processing.transactionProcessing(testListTransaction);
        Fruit expectedBanana = new Fruit("banana", 250);
        Fruit actualBanana = dao.getFruit("banana");
        Fruit expectedOrange = new Fruit("orange", 50);
        Fruit actualOrange = dao.getFruit("orange");
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedOrange, actualOrange);
    }

    @Test
    public void purchase_transaction_Ok() {
        List<Transaction> testListTransaction = new ArrayList<>();
        testListTransaction.add(balanceBanana);
        testListTransaction.add(purchaseBanana);
        processing.transactionProcessing(testListTransaction);
        String expectedName = "banana";
        int expectedAmount = 50;
        boolean expected = dao.getFruit(expectedName).getAmountFruit() == expectedAmount;
        assertTrue(expected);
    }
}
