package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NotEnoughFruitsException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionsProcessor;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operations.BalanceOperation;
import core.basesyntax.strategy.operations.OperationHandler;
import core.basesyntax.strategy.operations.PurchaseOperation;
import core.basesyntax.strategy.operations.ReturnOperation;
import core.basesyntax.strategy.operations.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionsProcessorImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
    private FruitsDao fruitsDao = new FruitsDaoImpl();
    private OperationStrategy operationStrategy;
    private FruitTransactionsProcessor fruitTransactionsProcessor;
    private FruitTransaction bananaBalance = new FruitTransaction();
    private FruitTransaction bananaOperation = new FruitTransaction();
    private List<FruitTransaction> fruitTransactions = new ArrayList<>();

    @Before
    public void setUp() {
        operationHandlerMap
                .put(FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitsDao));
        operationHandlerMap
                .put(FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitsDao));
        operationHandlerMap
                .put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation(fruitsDao));
        operationHandlerMap
                .put(FruitTransaction.Operation.RETURN, new ReturnOperation(fruitsDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitTransactionsProcessor = new FruitTransactionsProcessorImpl(operationStrategy);
        bananaBalance.setOperation(FruitTransaction.Operation.BALANCE);
        bananaBalance.setFruit("banana");
        bananaBalance.setQuantity(100);
        bananaOperation.setFruit("banana");
        fruitTransactions.add(bananaBalance);
    }

    @After
    public void afterClass() {
        fruitTransactions.clear();
    }

    @Test
    public void process_balanceProcessing_Ok() {
        fruitTransactionsProcessor.process(fruitTransactions);
        int actual = Storage.fruits.get("banana");
        assertEquals(100, actual);
    }

    @Test
    public void process_purchaseProcessing_Ok() {
        bananaOperation.setOperation(FruitTransaction.Operation.PURCHASE);
        bananaOperation.setQuantity(20);
        fruitTransactions.add(bananaOperation);
        fruitTransactionsProcessor.process(fruitTransactions);
        int actual = Storage.fruits.get("banana");
        assertEquals(80, actual);
    }

    @Test(expected = NotEnoughFruitsException.class)
    public void process_notEnoughFruitsForPurchase_NotOk() {
        bananaOperation.setOperation(FruitTransaction.Operation.PURCHASE);
        bananaOperation.setQuantity(120);
        fruitTransactions.add(bananaOperation);
        fruitTransactionsProcessor.process(fruitTransactions);
    }

    @Test
    public void process_returnProcessing_Ok() {
        bananaOperation.setOperation(FruitTransaction.Operation.RETURN);
        bananaOperation.setQuantity(20);
        fruitTransactions.add(bananaOperation);
        fruitTransactionsProcessor.process(fruitTransactions);
        int actual = Storage.fruits.get("banana");
        assertEquals(120, actual);
    }

    @Test
    public void process_supplyProcessing_Ok() {
        bananaOperation.setOperation(FruitTransaction.Operation.SUPPLY);
        bananaOperation.setQuantity(50);
        fruitTransactions.add(bananaOperation);
        fruitTransactionsProcessor.process(fruitTransactions);
        int actual = Storage.fruits.get("banana");
        assertEquals(150, actual);
    }

    @Test(expected = RuntimeException.class)
    public void process_TransactionsAreNull_NotOk() {
        fruitTransactionsProcessor.process(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_operationStrategyIsNull_NotOk() {
        FruitTransactionsProcessor fruitTransactionsWithNullStrategy
                = new FruitTransactionsProcessorImpl(null);
    }

}
