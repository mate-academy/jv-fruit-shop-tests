package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorImplTest {
    private static TransactionProcessor transactionProcessor;
    private static final List<FruitTransaction> testList = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        transactionProcessor = new TransactionProcessorImpl();
    }

    @Test
    public void process_listWithValidTransactions_ok() {
        testList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10));
        testList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 2));
        transactionProcessor.process(testList);
    }

    @Test
    public void process_FruitNameIsEmpty_ok() {
        testList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "", 10));
        transactionProcessor.process(testList);
    }

    @Test
    public void process_fruitNameIsNull_ok() {
        testList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                null, 10));
        transactionProcessor.process(testList);
    }

    @Test (expected = NullPointerException.class)
    public void process_fruitOperationIsNull_notOk() {
        testList.add(new FruitTransaction(null,
                "apple", 10));
        transactionProcessor.process(testList);
    }

    @Before
    public void setUp() {
        testList.clear();
    }
}
