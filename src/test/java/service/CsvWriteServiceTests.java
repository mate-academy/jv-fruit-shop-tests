package service;

import dao.TransactionDaoImpl;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.OperationStrategyImpl;

public class CsvWriteServiceTests {
    private CsvWriteService csvWriteService;
    private TransactionDaoImpl transactionDao;
    private CsvReadService csvReadService;

    @BeforeEach
    void setUp() {
        OperationStrategyImpl operationStrategyImpl = new OperationStrategyImpl();
        transactionDao = new TransactionDaoImpl(operationStrategyImpl);
        csvWriteService = new CsvWriteService(transactionDao.getAll());
        csvReadService = new CsvReadService();
        transactionDao.clearTransactions();
    }

    @Test
    void parseTransaction_WhenWrongLine() {
        String wrongFileName = "";
        String filePath = Paths.get("src", "main", "resources", wrongFileName).toString();
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            csvWriteService.exportToCsv(wrongFileName);
        });

        Assertions.assertTrue(exception.getMessage().contains(
                "Error writing to CSV file: ")
        );
    }

    @Test
    void processTransaction_WhenSuccess() {
        FruitTransaction[] transactions = {
                new FruitTransaction("Apple", 10, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("Banana", 5, FruitTransaction.Operation.BALANCE)
        };

        Arrays.stream(transactions).forEach(transactionDao::processTransaction);
        csvWriteService.exportToCsv("outputFile");

        List<String> lines = csvReadService.readTransactionsFromCsv("outputFile");
        Assertions.assertEquals("Apple,10", lines.get(0));
        Assertions.assertEquals("Banana,5", lines.get(1));
    }
}
