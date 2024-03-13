package service;

import dao.StorageDaoImpl;
import db.Storage;
import java.util.List;
import java.util.Map;
import model.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.operation.BalanceOperation;
import service.operation.OperationHandler;
import service.operation.OperationStrategyImpl;
import service.operation.PurchaseOperation;
import service.operation.ReturnOperation;
import service.operation.SupplyOperation;

class TransactionServiceImplTest {
    private static final Map<Transaction.Operation, OperationHandler> HANDLERS = Map.of(
            Transaction.Operation.BALANCE, new BalanceOperation(new StorageDaoImpl()),
            Transaction.Operation.RETURN, new ReturnOperation(new StorageDaoImpl()),
            Transaction.Operation.SUPPLY, new SupplyOperation(new StorageDaoImpl()),
            Transaction.Operation.PURCHASE, new PurchaseOperation(new StorageDaoImpl()));
    private static TransactionService transactionService;

    @BeforeAll
    static void setUp() {
        transactionService = new TransactionServiceImpl(
                new OperationStrategyImpl(HANDLERS));
    }

    @Test
    void processTransactions_singleTransaction_ok() {
        Transaction firstTransaction = new Transaction(Transaction.Operation.SUPPLY, "apple", 10);
        transactionService.processTransactions(List.of(firstTransaction));
        Assertions.assertEquals(10, Storage.STORAGE.get("apple"));
    }

    @Test
    void processTransactions_multipleTransactions_ok() {
        Transaction firstTransaction = new Transaction(Transaction.Operation.SUPPLY,
                "orange", 10);
        Transaction secondTransaction = new Transaction(Transaction.Operation.PURCHASE,
                "orange", 2);
        Transaction thirdTransaction = new Transaction(Transaction.Operation.RETURN,
                "orange", 4);
        transactionService.processTransactions(List.of(firstTransaction,
                secondTransaction, thirdTransaction));
        Assertions.assertEquals(12, Storage.STORAGE.get("orange"));
    }

    @AfterEach
    void clear() {
        Storage.STORAGE.clear();
    }
}
