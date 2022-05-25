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
        addFruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 40);
        addFruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        addFruitTransaction(FruitTransaction.Operation.RETURN, "apple", 15);
        addFruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10);
        processorService.process(fruitTransactions);
        Assert.assertEquals(expectedDB.entrySet(), Storage.fruitStorage.entrySet());
        Assert.assertEquals(expectedDB.size(), Storage.fruitStorage.size());
    }

    @Test
    public void process_transactionWithNullOperation_notOk() {
        addFruitTransaction(null, "banana", 15);
        addFruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 15);
        try {
            processorService.process(fruitTransactions);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void process_transactionWithNullFruitName_notOk() {
        addFruitTransaction(FruitTransaction.Operation.SUPPLY, null, 15);
        addFruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 15);
        try {
            processorService.process(fruitTransactions);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void process_transactionWithNegativeQuantity_notOk() {
        addFruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", -15);
        addFruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 5);
        try {
            processorService.process(fruitTransactions);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void process_transactionWithEmptyFruitName_notOk() {
        addFruitTransaction(FruitTransaction.Operation.SUPPLY, "", 15);
        addFruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 15);
        try {
            processorService.process(fruitTransactions);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    private void addFruitTransaction(FruitTransaction.Operation operation, String fruit,
                                     int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setQuantity(quantity);
        fruitTransaction.setFruit(fruit);
        fruitTransactions.add(fruitTransaction);
    }
}
