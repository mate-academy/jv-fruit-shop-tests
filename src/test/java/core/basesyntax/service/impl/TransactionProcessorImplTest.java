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
    public static void init() {
        transactionProcessor = new TransactionProcessorImpl();
    }

    @Test
    public void process_validTransactions_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 7));
        transactionProcessor.process(fruitTransactions);
    }

    @Test
    public void process_emptyList_ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        transactionProcessor.process(fruitTransactions);
    }
}
