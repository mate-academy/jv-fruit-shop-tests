package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorImplTest {
    private static TransactionProcessor transactionProcessor;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void init() {
        transactionProcessor = new TransactionProcessorImpl();
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void process_validTransactions_ok() {
        FruitTransaction balanceTransaction = new
                FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20);
        FruitTransaction purchaseTransaction = new
                FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
        List<FruitTransaction> fruitTransactions = List.of(
                balanceTransaction, purchaseTransaction);
        transactionProcessor.process(fruitTransactions);
        Integer expected = balanceTransaction.getQuantity() - purchaseTransaction.getQuantity();
        Integer actual = fruitDao.getQuantity("apple");
        assertEquals(String.format("Should return %d but was %d", expected, actual),
                expected, actual);
    }

    @Test
    public void process_emptyList_ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        transactionProcessor.process(fruitTransactions);
        assertTrue("Should return true but was false",
                fruitDao.isEmpty());
    }

    @After
    public void tearDown() {
        fruitDao.clearStorage();
    }
}
