package service.operation;

import dao.StorageDaoImpl;
import java.util.Map;
import model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final Map<Transaction.Operation, OperationHandler>
            MAP_WITH_MISSED_HANDLER = Map.of(
            Transaction.Operation.RETURN, new ReturnOperation(new StorageDaoImpl()),
            Transaction.Operation.SUPPLY, new SupplyOperation(new StorageDaoImpl()),
            Transaction.Operation.PURCHASE, new PurchaseOperation(new StorageDaoImpl()));
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void setUp() {
        operationStrategy = new OperationStrategyImpl(MAP_WITH_MISSED_HANDLER);
    }

    @Test
    void getOperation_missedHandler_notOk() {

        Transaction transaction = new Transaction(Transaction.Operation.BALANCE, "banana", 20);
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperation(transaction),
                "RuntimeException should be thrown for non existing handler");
    }
}
