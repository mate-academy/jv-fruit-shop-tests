package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static List<Transaction> validTransactions;
    private static Map<Transaction.Operation, OperationStrategy> handlers;
    private static List<String> resultList;
    private static TransactionProcessor transactionProcessor;

    @BeforeAll
    static void beforeAll() {
        resultList = new ArrayList<>();
        OperationStrategy operationStrategy = (fruit, quantity) -> resultList.add(fruit);
        handlers = Map.of(Transaction.Operation.BALANCE, operationStrategy);
        transactionProcessor = new TransactionProcessorImpl(handlers);
        validTransactions = List.of(new Transaction(Transaction.Operation.BALANCE, "banana", 20));
    }

    @AfterEach
    void tearDown() {
        resultList.clear();
    }

    @Test
    void createNewTransactionProcessor_nullHandlers_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new TransactionProcessorImpl(null),
                "NullPointerException must be thrown if provided argument is null");
    }

    @Test
    void createNewTransactionProcessor_emptyHandlers_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new TransactionProcessorImpl(Map.of()),
                "IllegalArgumentException must be thrown if handlers is empty");
    }

    @Test
    void process_validTransaction_ok() {
        List<String> expectedList = new ArrayList<>();
        validTransactions.forEach(transaction -> {
            expectedList.add(transaction.getFruit());
            transactionProcessor.process(transaction);
        });
        Assertions.assertEquals(expectedList, resultList);
    }
}
