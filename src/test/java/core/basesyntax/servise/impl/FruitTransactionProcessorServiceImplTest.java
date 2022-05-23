package core.basesyntax.servise.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.servise.FruitTransactionProcessorService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionProcessorServiceImplTest {
    private static FruitTransactionProcessorService processorService;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);
        processorService = new FruitTransactionProcessorServiceImpl(operationStrategy);
        fruitTransactions = new ArrayList<>();
    }

    @After
    public void after() {
        Storage.fruitStorage.clear();
        fruitTransactions.clear();
    }

    @Test
    public void process_validFruitTransactions_Ok() {
        Map<String, Integer> expectedDB = new HashMap<>();
        expectedDB.put("banana", 20);
        expectedDB.put("apple", 25);
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setFruit("banana");
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setQuantity(40);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setFruit("banana");
        secondTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        secondTransaction.setQuantity(20);
        FruitTransaction thirdTransaction = new FruitTransaction();
        thirdTransaction.setFruit("apple");
        thirdTransaction.setOperation(FruitTransaction.Operation.RETURN);
        thirdTransaction.setQuantity(15);
        FruitTransaction fourthTransaction = new FruitTransaction();
        fourthTransaction.setFruit("apple");
        fourthTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fourthTransaction.setQuantity(10);
        fruitTransactions.add(firstTransaction);
        fruitTransactions.add(secondTransaction);
        fruitTransactions.add(thirdTransaction);
        fruitTransactions.add(fourthTransaction);
        processorService.process(fruitTransactions);
        Assert.assertEquals(expectedDB.entrySet(), Storage.fruitStorage.entrySet());
        Assert.assertEquals(expectedDB.size(), Storage.fruitStorage.size());
    }

    @Test
    public void process_transactionWithNullOperation_notOk() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(null);
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(15);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondTransaction.setFruit("banana");
        secondTransaction.setQuantity(15);
        fruitTransactions.add(firstTransaction);
        fruitTransactions.add(secondTransaction);
        try {
            processorService.process(fruitTransactions);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void process_transactionWithNullFruitName_notOk() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        firstTransaction.setFruit(null);
        firstTransaction.setQuantity(15);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondTransaction.setFruit("banana");
        secondTransaction.setQuantity(15);
        fruitTransactions.add(firstTransaction);
        fruitTransactions.add(secondTransaction);
        try {
            processorService.process(fruitTransactions);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void process_transactionWithNegativeQuantity_notOk() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(-15);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondTransaction.setFruit("banana");
        secondTransaction.setQuantity(5);
        fruitTransactions.add(firstTransaction);
        fruitTransactions.add(secondTransaction);
        try {
            processorService.process(fruitTransactions);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void process_transactionWithEmptyFruitName_notOk() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        firstTransaction.setFruit("");
        firstTransaction.setQuantity(15);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondTransaction.setFruit("banana");
        secondTransaction.setQuantity(15);
        fruitTransactions.add(firstTransaction);
        fruitTransactions.add(secondTransaction);
        try {
            processorService.process(fruitTransactions);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }
}
