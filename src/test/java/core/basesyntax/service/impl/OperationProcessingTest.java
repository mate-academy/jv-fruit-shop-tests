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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OperationProcessingTest {
    Map<Transaction.Operation, TransactionsHandler> strategy = new HashMap<>();
    StorageDao storageDao = new StorageDaoImpl();
    TransactionsHandler balanceOperationHandler = new BalanceOperationHandlerImpl(storageDao);
    TransactionsHandler supplyOperationHandler = new SupplyOperationHandlerImpl(storageDao);
    TransactionsHandler purchaseOperationHandler = new PurchaseOperationHandlerImpl(storageDao);
    TransactionsHandler returnOperationHandler = new ReturnOperationHandlerImpl(storageDao);

    @Before
    public void setUp() {
        strategy.put(Transaction.Operation.BALANCE, balanceOperationHandler);
        strategy.put(Transaction.Operation.SUPPLY, supplyOperationHandler);
        strategy.put(Transaction.Operation.PURCHASE, purchaseOperationHandler);
        strategy.put(Transaction.Operation.RETURN, returnOperationHandler);
    }

    @Test
    public void OperationProcessingStrategy_Ok() {
        OperationProcessingStrategy operationProcessingStrategy =
            new OperationProcessingStrategyImpl(strategy);
        TransactionsHandler actual =
                operationProcessingStrategy.get(Transaction.Operation.BALANCE);
        TransactionsHandler expected = balanceOperationHandler;
        assertEquals(expected, actual);
        actual = operationProcessingStrategy.get(Transaction.Operation.SUPPLY);
        expected = supplyOperationHandler;
        assertEquals(expected, actual);
        actual = operationProcessingStrategy.get(Transaction.Operation.PURCHASE);
        expected = purchaseOperationHandler;
        assertEquals(expected, actual);
        actual = operationProcessingStrategy.get(Transaction.Operation.RETURN);
        expected = returnOperationHandler;
        assertEquals(expected, actual);
    }

    @Test
    public void OperationProcessingStrategy_NullOperation_Ok() {
        OperationProcessingStrategy operationProcessingStrategy =
                new OperationProcessingStrategyImpl(strategy);
        TransactionsHandler actual =
                operationProcessingStrategy.get(null);
        Assert.assertNull(actual);
    }

    @Test
    public void OperationProcessingService_Ok() {
        OperationProcessingStrategy operationProcessingStrategy =
                new OperationProcessingStrategyImpl(strategy);
        OperationProcessor operationProcessor =
                new OperationProcessingServiceImpl(operationProcessingStrategy);
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction =
                new Transaction(Transaction.Operation.BALANCE, "Lemon", 100);
        transactionList.add(0, transaction);
        operationProcessor.process(transactionList);
        TransactionsHandler actual = operationProcessingStrategy.get(transaction.getTypeOperation());
        TransactionsHandler expected = balanceOperationHandler;
        assertEquals(expected, actual);
        assertEquals(100, (int) storageDao.getRemainingGoods("Lemon"));
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}
