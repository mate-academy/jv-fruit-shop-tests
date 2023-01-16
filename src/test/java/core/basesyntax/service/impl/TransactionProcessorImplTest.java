package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorImplTest {
    public static final String BANANA = "banana";
    public static final String APPLE = "apple";
    public static final String PINEAPPLE = "pineapple";
    private static TransactionProcessor transactionProcessor;

    @BeforeClass
    public static void setUp() {
        transactionProcessor = new TransactionProcessorImpl();
    }

    @Test
    public void process_validInputTransactions_ok() {
        List<FruitTransaction> actual = new ArrayList<>();
        actual.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        actual.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        actual.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "pineapple", 30));
        actual.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        actual.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "pineapple", 25));
        actual.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        actual.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "pineapple", 50));
        actual.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        transactionProcessor.process(actual);
        Assert.assertTrue("Incorrect result: expected banana of quantity" + 107,
                Storage.fruits.containsKey(BANANA) && Storage.fruits.get(BANANA) == 107);
        Assert.assertTrue("Incorrect result: expected apple of quantity" + 110,
                Storage.fruits.containsKey(APPLE) && Storage.fruits.get(APPLE) == 110);
        Assert.assertTrue("Incorrect result: expected pineapple of quantity" + 55,
                Storage.fruits.containsKey(PINEAPPLE) && Storage.fruits.get(PINEAPPLE) == 55);
    }
}
