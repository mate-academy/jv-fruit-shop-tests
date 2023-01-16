package core.basesyntax.service.impl;

import core.basesyntax.db.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorImplTest {
    private static List<FruitTransaction> transactions;
    private static TransactionProcessor transactionProcessor;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        transactionProcessor = new TransactionProcessorImpl();
        transactions = new ArrayList<>();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void process_validData_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("dragon fruit");
        fruitTransaction.setQuantity(100);
        transactions.add(fruitTransaction);
        transactionProcessor.process(transactions);
        Integer expected = 100;
        Integer actual = FruitDao.getQuantity("dragon fruit");
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void tearDown() {
        FruitDao.storage.clear();
        transactions.clear();
    }
}
