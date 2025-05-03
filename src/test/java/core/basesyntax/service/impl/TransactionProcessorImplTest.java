package core.basesyntax.service.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.DecrementHandler;
import core.basesyntax.strategy.IncrementHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private TransactionProcessorImpl transactionProcessor;
    private Fruit apple;

    @BeforeEach
    void setUp() {
        StorageServiceImpl storageService = new StorageServiceImpl();
        apple = new Fruit("apple");
        Map<Transaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Transaction.Operation.BALANCE, new IncrementHandler(storageService));
        handlers.put(Transaction.Operation.PURCHASE, new DecrementHandler(storageService));
        handlers.put(Transaction.Operation.RETURN, new IncrementHandler(storageService));
        handlers.put(Transaction.Operation.SUPPLY, new IncrementHandler(storageService));
        transactionProcessor = new TransactionProcessorImpl(handlers);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void process_validBalanceUpdating_ok() {
        List<Transaction> transactions = List.of(new Transaction(
                Transaction.Operation.BALANCE, apple, 50));
        transactionProcessor.process(transactions);
        assertEquals(50, Storage.getFruitQuantity(apple));
    }

    @Test
    void process_validReturnUpdating_ok() {
        List<Transaction> transactions = List.of(new Transaction(
                Transaction.Operation.RETURN, apple, 50));
        transactionProcessor.process(transactions);
        assertEquals(50, Storage.getFruitQuantity(apple));
    }

    @Test
    void process_validSupplyUpdating_ok() {
        List<Transaction> transactions = List.of(new Transaction(
                Transaction.Operation.SUPPLY, apple, 50));
        transactionProcessor.process(transactions);
        assertEquals(50, Storage.getFruitQuantity(apple));
    }

    @Test
    void process_validPurchaseUpdating_ok() {
        Storage.updateFruitQuantity(apple, 60);
        List<Transaction> purchaseTransactions = List.of(new Transaction(
                Transaction.Operation.PURCHASE, apple, 50));
        transactionProcessor.process(purchaseTransactions);
        assertEquals(10, Storage.getFruitQuantity(apple));
    }

    @Test
    void process_invalidOperation_notOk() {
        List<Transaction> transactions = List.of(new Transaction(
                null, apple, 50));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> transactionProcessor.process(transactions))
                .withMessage("No handler found for operation: null");
    }
}
