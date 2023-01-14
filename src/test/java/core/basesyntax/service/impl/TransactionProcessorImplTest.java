package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorImplTest {
    private static TransactionProcessor transactionProcessor;

    @BeforeClass
    public static void beforeClass() {
        transactionProcessor = new TransactionProcessorImpl();
    }

    @Test
    public void process_listWithValidTransactions_ok() {
        List<FruitTransaction> testList = new ArrayList<>(
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 2)));
        transactionProcessor.process(testList);
    }

    @Test
    public void process_FruitNameIsEmpty_ok() {
        List<FruitTransaction> testList = new ArrayList<>(
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "", 10)));
        transactionProcessor.process(testList);
    }

    @Test
    public void process_fruitNameIsNull_ok() {
        List<FruitTransaction> testList = new ArrayList<>(
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 10)));
        transactionProcessor.process(testList);
    }

    @Test (expected = NullPointerException.class)
    public void process_fruitOperationIsNull_notOk() {
        List<FruitTransaction> testList = new ArrayList<>(
                List.of(new FruitTransaction(null, "apple", 10)));
        transactionProcessor.process(testList);
    }
}
