package service;

import static java.util.Arrays.stream;

import dao.TransactionDaoImpl;
import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.OperationStrategyImpl;

public class TransactionServiceTests {
  private TransactionDaoImpl transactionDao;
  private CsvParseService csvParseService;

  @BeforeEach
  void setUp() {
    OperationStrategyImpl operationStrategyImpl = new OperationStrategyImpl();
    transactionDao = new TransactionDaoImpl(operationStrategyImpl);
    csvParseService = new CsvParseService();
    transactionDao.clearTransactions();
  }

  @Test
  void parseTransaction_WhenWrongLine() {
    FruitTransaction[] transactions = {
      new FruitTransaction("Apple", 10, FruitTransaction.Operation.BALANCE),
      new FruitTransaction("Banana", 5, FruitTransaction.Operation.BALANCE)
    };
    stream(transactions).forEach(transactionDao::processTransaction);
    Assertions.assertEquals(2, transactionDao.getAll().size());
  }

  @Test
  void parseTransaction_WhenExist() {
    FruitTransaction transactions =
        new FruitTransaction("Apple", 10, FruitTransaction.Operation.BALANCE);
    transactionDao.processTransaction(transactions);
    Assertions.assertEquals(1, transactionDao.getAll().size());
    transactionDao.processTransaction(transactions);
    Assertions.assertEquals(1, transactionDao.getAll().size());
  }

  @Test
  void parseTransaction_WhenTransactionSuccess() {
    String[] mockLines = {"p,apple,20", "p,banana,5"};
    Assertions.assertEquals(0, transactionDao.getAll().size());
    List<FruitTransaction> transactions =
        Arrays.stream(mockLines).map(csvParseService::parseTransaction).toList();
    CsvTransactionService csvTransactionService = new CsvTransactionService(transactionDao);
    Assertions.assertEquals(2, csvTransactionService.processCsv(transactions).values().size());
  }
}
