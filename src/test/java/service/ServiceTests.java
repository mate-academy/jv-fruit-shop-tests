package service;

import dao.TransactionDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class ServiceTests {
    private TransactionDaoImpl transactionDao;

    @BeforeEach
    void setUp() {
        transactionDao = new TransactionDaoImpl();
        Storage.transactions.clear(); // Reset storage before each test
    }

    @Test
    public void processTransaction_Balance_ShouldSetCorrectQuantity() {
        FruitTransaction transaction = new FruitTransaction("apple", 50, FruitTransaction.Operation.BALANCE);
        transactionDao.processTransaction(transaction);

        FruitTransaction storedTransaction = transactionDao.getTransactionByName("apple");
        Assertions.assertNotNull(storedTransaction);
        Assertions.assertEquals(50, storedTransaction.getQuantity());
    }
}
