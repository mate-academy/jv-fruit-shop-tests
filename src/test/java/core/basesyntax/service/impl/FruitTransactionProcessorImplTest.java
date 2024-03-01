package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransactionProcessorImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationsHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorImplTest {
    private static FruitTransactionProcessor processor;
    private static Map<FruitTransaction.Operation, OperationsHandler> handlersMap = new HashMap<>();

    @BeforeAll
    public static void setUp() {
        handlersMap.put(FruitTransaction.Operation.BALANCE, transaction ->
                System.out.println("Balancing..."));
        handlersMap.put(FruitTransaction.Operation.SUPPLY, transaction ->
                System.out.println("Supplying..."));
        handlersMap.put(FruitTransaction.Operation.PURCHASE, transaction ->
                System.out.println("Purchasing..."));
        handlersMap.put(FruitTransaction.Operation.RETURN, transaction ->
                System.out.println("Returning..."));

        OperationStrategy operationStrategy = new OperationStrategy(handlersMap);
        processor = new FruitTransactionProcessorImpl(operationStrategy);
    }

    @Test
    void processTransactions_ValidTransactions_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 30)
        );
        assertDoesNotThrow(() -> processor.processTransactions(transactions));
    }

    @Test
    void processTransactions_EmptyList_Ok() {
        List<FruitTransaction> transactions = List.of();
        assertDoesNotThrow(() ->
                processor.processTransactions(transactions),
                "Processing an empty list should not throw an exception.");
    }

    @Test
    void processTransactions_NullList_NotOk() {
        assertThrows(NullPointerException.class, () -> processor.processTransactions(null),
                "Processing a null list of transactions should throw NullPointerException.");
    }
}
