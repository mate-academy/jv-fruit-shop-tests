package service;

import dao.TransactionDaoImpl;
import java.util.Arrays;
import model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.OperationStrategyImpl;

public class TransactionServiceTests {
    private TransactionDaoImpl transactionDao;

    @BeforeEach
    void setUp() {
        OperationStrategyImpl operationStrategyImpl = new OperationStrategyImpl();
        transactionDao = new TransactionDaoImpl(operationStrategyImpl);
        transactionDao.clearTransactions();
    }

    @Test
    void parseTransaction_WhenWrongLine() {
        FruitTransaction[] transactions = {
                new FruitTransaction("Apple", 10, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("Banana", 5, FruitTransaction.Operation.BALANCE)
        };
        Arrays.stream(transactions).forEach(transactionDao::processTransaction);
        Assertions.assertEquals(2, transactionDao.getAll().size());
    }

    @Test
    void parseTransaction_WhenExist() {
        FruitTransaction transactions
                = new FruitTransaction("Apple", 10, FruitTransaction.Operation.BALANCE);
        transactionDao.processTransaction(transactions);
        Assertions.assertEquals(1, transactionDao.getAll().size());
        transactionDao.processTransaction(transactions);
        Assertions.assertEquals(1, transactionDao.getAll().size());
    }
}
