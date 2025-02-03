package dao;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionDaoTests {
    private TransactionDaoImpl transactionDao;

    @BeforeEach
    void setUp() {
        transactionDao = new TransactionDaoImpl();
        Storage.transactions.clear(); // Ensure a fresh state before each test
    }

    @Test
    void processTransaction_WhenBalanceOperation() {
        FruitTransaction transaction =
                new FruitTransaction("apple", 50, FruitTransaction.Operation.BALANCE);
        transactionDao.processTransaction(transaction);

        Assertions.assertEquals(50, transactionDao.getTransactionByName("apple").getQuantity());
    }

    @Test
    void processTransaction_WhenSupplyOperation_Ok() {
        FruitTransaction transactionBalance =
                new FruitTransaction("apple", 150, FruitTransaction.Operation.BALANCE);
        FruitTransaction transactionSupply =
                new FruitTransaction("apple", 50, FruitTransaction.Operation.SUPPLY);
        transactionDao.processTransaction(transactionBalance);
        transactionDao.processTransaction(transactionSupply);

        Assertions.assertEquals(200, transactionDao.getTransactionByName("apple").getQuantity());
    }

    @Test
    void processTransaction_WhenReturnOperation_Ok() {
        FruitTransaction transactionBalance =
                new FruitTransaction("apple", 150, FruitTransaction.Operation.BALANCE);
        FruitTransaction transactionReturn =
                new FruitTransaction("apple", 30, FruitTransaction.Operation.RETURN);

        transactionDao.processTransaction(transactionBalance);
        transactionDao.processTransaction(transactionReturn);

        Assertions.assertEquals(180, transactionDao.getTransactionByName("apple").getQuantity());
    }

    @Test
    void processTransaction_WhenPurchaseOperation_Ok() {
        FruitTransaction transactionBalance =
                new FruitTransaction("apple", 150, FruitTransaction.Operation.BALANCE);
        FruitTransaction transactionReturn =
                new FruitTransaction("apple", 30, FruitTransaction.Operation.PURCHASE);
        transactionDao.processTransaction(transactionBalance);
        transactionDao.processTransaction(transactionReturn);
        Assertions.assertEquals(120, transactionDao.getTransactionByName("apple").getQuantity());
    }

    @Test
    void processTransaction_WhenGetAll_Ok() {
        FruitTransaction appleBalance =
                new FruitTransaction("apple", 150, FruitTransaction.Operation.BALANCE);
        FruitTransaction bananaBalance =
                new FruitTransaction("banana", 150, FruitTransaction.Operation.BALANCE);
        FruitTransaction transactionReturn =
                new FruitTransaction("apple", 30, FruitTransaction.Operation.PURCHASE);

        transactionDao.processTransaction(appleBalance);
        transactionDao.processTransaction(bananaBalance);

        Assertions.assertEquals(2, transactionDao.getAll().size());
    }

    @Test
    void processTransaction_ShouldThrowException_ForUnknownOperation() {
        FruitTransaction invalidTransaction = new FruitTransaction("kiwi", 10, null);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transactionDao.processTransaction(invalidTransaction);
        });

        Assertions.assertTrue(exception.getMessage().contains("Unknown operation"));
    }

    @Test
    void processTransaction_WhenBlackFriday_Ok() {
        FruitTransaction transactionBalance =
                new FruitTransaction("apple", 1000, FruitTransaction.Operation.BALANCE);
        FruitTransaction transactionPurchase =
                new FruitTransaction("apple", 500, FruitTransaction.Operation.PURCHASE);
        FruitTransaction transactionSupply =
                new FruitTransaction("apple", 200, FruitTransaction.Operation.SUPPLY);
        transactionDao.processTransaction(transactionBalance);
        transactionDao.processTransaction(transactionPurchase);
        transactionDao.processTransaction(transactionPurchase);
        transactionDao.processTransaction(transactionSupply);
        Assertions.assertEquals(200, transactionDao.getTransactionByName("apple").getQuantity());

    }
}
