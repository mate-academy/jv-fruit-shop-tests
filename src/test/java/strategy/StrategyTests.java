package strategy;

import dao.TransactionDaoImpl;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CsvTransactionService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTests {
    private TransactionDaoImpl transactionDao;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, TransactionHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler());

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        transactionDao = new TransactionDaoImpl();
        transactionDao.clearTransactions();
    }

    @Test
    void balanceHandler_ShouldAddQuantityToExistingStock() {
        BalanceHandler balanceHandler = new BalanceHandler();
        FruitTransaction transaction = new FruitTransaction("apple", 10, FruitTransaction.Operation.BALANCE);
        int updatedQuantity = balanceHandler.apply(5, transaction);
        assertEquals(15, updatedQuantity, "Balance operation should add quantity to existing stock");
    }

    @Test
    void operationStrategyImpl_ShouldReturnCorrectHandler() {
        Map<FruitTransaction.Operation, TransactionHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        assertInstanceOf(BalanceHandler.class, operationStrategy.getStrategy(FruitTransaction.Operation.BALANCE), "Should return correct handler for BALANCE operation");
    }

    @Test
    void operationStrategyImpl_WhenUnknownOperation_ShouldThrowException() {
        Map<FruitTransaction.Operation, TransactionHandler> operationHandlers = new HashMap<>();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> operationStrategy.getStrategy(null));
        assertTrue(exception.getMessage().contains("Unknown operation"));
    }

    @Test
    void operationStrategyImpl_ShouldReturnCorrectHandler_Return() {
        Map<FruitTransaction.Operation, TransactionHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        assertInstanceOf(ReturnHandler.class, operationStrategy.getStrategy(FruitTransaction.Operation.RETURN), "Should return correct handler for RETURN operation");
    }

    @Test
    void returnHandler_ShouldIncreaseQuantityInStorage() {
        ReturnHandler returnHandler = new ReturnHandler();
        FruitTransaction transaction = new FruitTransaction("banana", 5, FruitTransaction.Operation.RETURN);
        int updatedQuantity = returnHandler.apply(10, transaction);
        assertEquals(15, updatedQuantity, "Return operation should increase quantity in storage");
    }
}
