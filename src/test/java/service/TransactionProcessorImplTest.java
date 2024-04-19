package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.model.Operation;
import core.basesyntax.basesyntax.service.TransactionProcessor;
import core.basesyntax.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.OperationStrategy;
import core.basesyntax.basesyntax.strategy.impl.BalanceHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static Map<Operation, OperationHandler> operationMap;
    private static TransactionProcessor transactionService;

    @BeforeAll
    static void beforeAll() {
        operationMap = new HashMap<>();
        operationMap.put(Operation.BALANCE, new BalanceHandlerImpl());
    }

    @BeforeEach
    void setUp() {
        transactionService = new TransactionProcessorImpl(new OperationStrategy(operationMap));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void process_nullData_notOk() {
        assertThrows(NullPointerException.class, () -> transactionService.process(null));
    }

    @Test
    void process_validData_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(20);
        List<FruitTransaction> transactions = List.of(transaction);

        transactionService.process(transactions);
        int expected = 20;
        int actual = Storage.fruits.get("banana");

        assertEquals(expected, actual, "Storage should reflect the processed transaction.");
    }
}
