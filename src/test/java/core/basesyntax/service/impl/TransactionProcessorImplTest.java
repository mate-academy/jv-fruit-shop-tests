package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorImplTest {
    private static TransactionProcessor transactionProcessor;

    @BeforeClass
    public static void setUp() {
        transactionProcessor = new TransactionProcessorImpl();
    }

    @Test
    public void process_validTransactions_ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 6));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 3));
        int expected = 17;
        transactionProcessor.process(transactions);
        int actual = FruitStorage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void process_nullTransactions_notOk() {
        transactionProcessor.process(null);
    }
}
