package core.basesyntax.service.impl;

import core.basesyntax.db.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class TransactionProcessorImplTest {

    @Test
    public void process_validData_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("dragon fruit");
        fruitTransaction.setQuantity(100);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransaction);
        TransactionProcessor transactionProcessor = new TransactionProcessorImpl();
        transactionProcessor.process(transactions);
        Integer expected = 100;
        Integer actual = FruitDao.getQuantity("dragon fruit");
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void tearDown() {
        FruitDao.storage.clear();
    }
}
