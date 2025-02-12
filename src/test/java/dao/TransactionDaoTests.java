package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TransactionDaoImplTests {

    private TransactionDaoImpl transactionDao;

    @BeforeEach
    void setUp() {
        transactionDao = new TransactionDaoImpl();
        transactionDao.clearTransactions();
    }

    @Test
    void saveTransaction_WhenNewTransaction_StoresCorrectly() {
        transactionDao.saveTransaction("Apple", 15);
        transactionDao.saveTransaction("Banana", 10);

        Map<String, Integer> allTransactions = transactionDao.getAll();

        assertEquals(2, allTransactions.size(), "Storage should contain 2 items");
        assertEquals(15, allTransactions.get("Apple"), "Apple should have 15 quantity");
        assertEquals(10, allTransactions.get("Banana"), "Banana should have 10 quantity");
    }

    @Test
    void getTransactionByName_WhenFruitExists_ReturnsCorrectQuantity() {
        transactionDao.saveTransaction("Apple", 25);
        assertEquals(25, transactionDao.getTransactionByName(
                "Apple"),
            "Should return 25 for Apple"
        );
    }

    @Test
    void getTransactionByName_WhenFruitNotExists_ReturnsZero() {
        assertEquals(0, transactionDao.getTransactionByName(
                "NonExistentFruit"),
            "Should return 0 for unknown fruit"
        );
    }

    @Test
    void clearTransactions_WhenCalled_EmptiesStorage() {
        transactionDao.saveTransaction("Apple", 20);
        transactionDao.saveTransaction("Banana", 10);
        transactionDao.clearTransactions();
        assertTrue(transactionDao.getAll().isEmpty(), "Storage should be empty after clearing");
    }
}
