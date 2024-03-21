package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.repository.StorageRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static final String NULL_OPERATION_MESSAGE
            = "Can't apply strategy for null transactions";
    private static final List<Transaction> TRANSACTIONS = new ArrayList<>();
    private StorageRepository repository = new StorageRepository();
    private OperationHandler operationHandler = new OperationHandler(repository);

    @BeforeAll
    static void setUp() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(Operation.BALANCE, "banana", 20));
        transactions.add(new Transaction(Operation.BALANCE, "apple", 100));
        transactions.add(new Transaction(Operation.SUPPLY, "banana", 100));
        transactions.add(new Transaction(Operation.PURCHASE, "banana", 13));
        transactions.add(new Transaction(Operation.RETURN, "apple", 10));
        transactions.add(new Transaction(Operation.PURCHASE, "apple", 20));
        transactions.add(new Transaction(Operation.PURCHASE, "banana", 5));
        transactions.add(new Transaction(Operation.SUPPLY, "banana", 50));
        TRANSACTIONS.addAll(transactions);
    }

    @AfterEach
    void cleanUp() {
        repository.getProducts().clear();
    }

    @Test
    void executeStrategy_validTransactions_ok() {
        operationHandler.executeStrategy(TRANSACTIONS);
        assertEquals(152, repository.getProducts().get("banana"));
        assertEquals(90, repository.getProducts().get("apple"));
    }

    @Test
    void executeStrategy_nullTransactions_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.executeStrategy(null);
        });
        assertEquals(NULL_OPERATION_MESSAGE, exception.getMessage());
    }
}
