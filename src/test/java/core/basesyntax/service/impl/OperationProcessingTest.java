package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationProcessingStrategy;
import core.basesyntax.service.OperationProcessor;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.TransactionsHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationProcessingTest {
    private Map<Transaction.Operation, TransactionsHandler> strategy = new HashMap<>();
    private StorageDao storageDao = new StorageDaoImpl();
    private TransactionsHandler balanceOperationHandler;
    private TransactionsHandler supplyOperationHandler;
    private TransactionsHandler purchaseOperationHandler;
    private TransactionsHandler returnOperationHandler;

    @Before
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandlerImpl(storageDao);
        supplyOperationHandler = new SupplyOperationHandlerImpl(storageDao);
        purchaseOperationHandler = new PurchaseOperationHandlerImpl(storageDao);
        returnOperationHandler = new ReturnOperationHandlerImpl(storageDao);
        strategy.put(Transaction.Operation.BALANCE, balanceOperationHandler);
        strategy.put(Transaction.Operation.SUPPLY, supplyOperationHandler);
        strategy.put(Transaction.Operation.PURCHASE, purchaseOperationHandler);
        strategy.put(Transaction.Operation.RETURN, returnOperationHandler);
    }

    @Test
    public void processing_strategy_Ok() {
        OperationProcessingStrategy operationProcessingStrategy =
                new OperationProcessingStrategyImpl(strategy);
        TransactionsHandler actual =
                operationProcessingStrategy.get(Transaction.Operation.BALANCE);
        TransactionsHandler expected = balanceOperationHandler;
        Assert.assertEquals(expected, actual);
        actual = operationProcessingStrategy.get(Transaction.Operation.SUPPLY);
        expected = supplyOperationHandler;
        Assert.assertEquals(expected, actual);
        actual = operationProcessingStrategy.get(Transaction.Operation.PURCHASE);
        expected = purchaseOperationHandler;
        Assert.assertEquals(expected, actual);
        actual = operationProcessingStrategy.get(Transaction.Operation.RETURN);
        expected = returnOperationHandler;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void processing_strategyNullOperation_Ok() {
        OperationProcessingStrategy operationProcessingStrategy =
                new OperationProcessingStrategyImpl(strategy);
        TransactionsHandler actual =
                operationProcessingStrategy.get(null);
        Assert.assertNull(actual);
    }

    @Test
    public void processing_service_Ok() {
        OperationProcessingStrategy operationProcessingStrategy =
                new OperationProcessingStrategyImpl(strategy);
        OperationProcessor operationProcessor =
                new OperationProcessingServiceImpl(operationProcessingStrategy);
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction =
                new Transaction(Transaction.Operation.BALANCE, "Lemon", 100);
        transactionList.add(0, transaction);
        operationProcessor.process(transactionList);
        TransactionsHandler actual =
                operationProcessingStrategy.get(transaction.getTypeOperation());
        TransactionsHandler expected = balanceOperationHandler;
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(100, (int) storageDao.getRemainingGoods("Lemon"));
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}
