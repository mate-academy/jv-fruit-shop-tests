package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static Transaction validTransaction;

    @BeforeAll
    static void beforeAll() {
        validTransaction = new Transaction(Transaction.Operation.BALANCE, "banana", 20);
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
        List<String> expected = List.of(validTransaction.getFruit());
        List<String> actual = new ArrayList<>();
        OperationStrategy operationStrategy = (fruit, quantity) -> actual.add(fruit);
        Map<Transaction.Operation, OperationStrategy> handlers = Map.of(
                Transaction.Operation.BALANCE, operationStrategy);
        TransactionProcessor transactionProcessor = new TransactionProcessorImpl(handlers);

        transactionProcessor.process(validTransaction);

        Assertions.assertEquals(expected, actual);
    }
}
