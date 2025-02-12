package strategy;

import static org.junit.jupiter.api.Assertions.*;

import dao.TransactionDaoImpl;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CsvParseService;
import service.CsvTransactionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CsvTransactionServiceTest {

    private CsvTransactionService csvTransactionService;
    private TransactionDaoImpl transactionDao;
    private CsvParseService csvParseService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, TransactionHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler());

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        transactionDao = new TransactionDaoImpl();
        csvTransactionService = new CsvTransactionService(transactionDao, operationStrategy);
        csvParseService = new CsvParseService();
        transactionDao.clearTransactions();
    }

    @Test
    void processCsv_WhenEmptyTransactionList_NoChangesInStorage() {
        csvTransactionService.processCsv(List.of());
        assertEquals(
            0,
            transactionDao.getAll().size(),
            "Storage should remain empty if no transactions are processed");
    }

    @Test
    void processCsv_WhenValidSupplyTransaction_StorageUpdatedCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
            "banana",
            20,
            FruitTransaction.Operation.SUPPLY);
        csvTransactionService.processCsv(List.of(transaction));
        assertEquals(
            20,
            transactionDao.getAll().get("banana"),
            "Storage should be updated correctly after supply transaction");
    }

    @Test
    void processCsv_WhenValidPurchaseTransaction_StorageDecreasedCorrectly() {
        FruitTransaction supplyTransaction = new FruitTransaction(
            "orange",
            30,
            FruitTransaction.Operation.SUPPLY);
        FruitTransaction purchaseTransaction = new FruitTransaction(
            "orange",
            10,
            FruitTransaction.Operation.PURCHASE);
        csvTransactionService.processCsv(List.of(supplyTransaction, purchaseTransaction));
        assertEquals(
            20,
            transactionDao.getAll().get("orange"),
            "Storage should decrease correctly after purchase transaction");
    }

    @Test
    void parseTransactions_WhenEmptyList_ShouldThrowException() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> csvParseService.parseTransactions(null));
        assertEquals("CSV file is empty.", exception.getMessage());
    }

    @Test
    void parseTransaction_WhenInvalidFormat_ShouldThrowException() {
        String invalidLine = "apple,10";
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> csvParseService.parseTransaction(invalidLine));
        assertTrue(exception.getMessage().contains("Invalid CSV format"));
    }

    @Test
    void parseTransaction_WhenValidFormat_ShouldReturnFruitTransaction() {
        String validLine = "s,apple,10";
        FruitTransaction transaction = csvParseService.parseTransaction(validLine);
        assertEquals("apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }
}
