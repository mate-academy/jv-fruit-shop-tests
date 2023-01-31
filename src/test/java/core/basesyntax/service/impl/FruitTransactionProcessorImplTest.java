package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionProcessor;
import core.basesyntax.strategy.OperationCalculator;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.CreditOperationHandler;
import core.basesyntax.strategy.impl.DebitOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionProcessorImplTest {
    private static FruitTransactionProcessor fruitTransactionProcessor;

    @BeforeClass
    public static void beforeAll() {
        Map<FruitTransaction.Operation, OperationCalculator> operationsMap = Map.of(
                FruitTransaction.Operation.BALANCE, new CreditOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new CreditOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new DebitOperationHandler(),
                FruitTransaction.Operation.RETURN, new CreditOperationHandler()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationsMap);
        fruitTransactionProcessor = new FruitTransactionProcessorImpl(operationStrategy);
    }

    @Test
    public void handleData_ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 25));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 14));
        Map<String, Integer> actual = fruitTransactionProcessor.process(transactions);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 25);
        expected.put("apple", 14);
        String message = "Data calculated incorrectly.";
        Assert.assertEquals(message, expected, actual);
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 6));
        expected.put("banana", 10);
        expected.put("apple", 20);
        actual = fruitTransactionProcessor.process(transactions);
        Assert.assertEquals(message, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handleData_negativeValue_notOk() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 8));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 17));
        fruitTransactionProcessor.process(transactions);
    }

    @Test(expected = NullPointerException.class)
    public void handleData_nullTransaction_notOk() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(null);
        fruitTransactionProcessor.process(transactions);
    }

    @Test(expected = NullPointerException.class)
    public void handleData_nullValues_notOk() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(null, null, 200));
        fruitTransactionProcessor.process(transactions);
    }
}
