package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.IncrementHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private StorageServiceImpl storageService;
    private TransactionProcessorImpl transactionProcessor;
    private Fruit apple;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        apple = new Fruit("apple");
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void process_validDataUpdating_ok() {
        Map<Transaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Transaction.Operation.BALANCE, new IncrementHandler(storageService));
        transactionProcessor = new TransactionProcessorImpl(handlers);
        List<Transaction> transactions = List.of(new Transaction(
                Transaction.Operation.BALANCE, apple, 50));
        transactionProcessor.process(transactions);
        assertEquals(50, Storage.getFruitQuiantity(apple));
    }

    @Test
    void process_invalidOperation_notOk() {
        Map<Transaction.Operation, OperationHandler> handlers = new HashMap<>();
        transactionProcessor = new TransactionProcessorImpl(handlers);
        List<Transaction> transactions
                = List.of(new Transaction(Transaction.Operation.BALANCE, apple, 50));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transactionProcessor.process(transactions));
        assertEquals("No handler found for operation: BALANCE", exception.getMessage());
    }
}
