package service.operation;

import dao.StorageDaoImpl;
import java.util.Map;
import model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final StorageDaoImpl storageDao = new StorageDaoImpl();
    private static final Map<Transaction.Operation, OperationHandler>
            MAP_WITH_MISSED_HANDLER = Map.of(
            Transaction.Operation.RETURN, new ReturnOperation(storageDao),
            Transaction.Operation.SUPPLY, new SupplyOperation(storageDao),
            Transaction.Operation.PURCHASE, new PurchaseOperation(storageDao));
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void setUp() {
        operationStrategy = new OperationStrategyImpl(MAP_WITH_MISSED_HANDLER);
    }

    @Test
    void getOperation_existingHandler_ok() {
        Transaction transaction = new Transaction(Transaction.Operation.RETURN, "banana", 10);
        OperationHandler handler = operationStrategy.getOperation(transaction);
        Assertions.assertInstanceOf(ReturnOperation.class, handler);
    }

    @Test
    void getOperation_missedHandler_notOk() {
        Transaction transaction = new Transaction(Transaction.Operation.BALANCE, "banana", 20);
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperation(transaction),
                "RuntimeException should be thrown for non existing handler");
    }
}
