package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorServiceImplTest {
    private List<FruitTransaction> transactions = new ArrayList<>();
    private TransactionProcessorServiceImpl transactionProcessorServiceImpl;
    private Map<FruitTransaction.Operation, OperationStrategy> strategies;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        strategies = new HashMap<>();
        transactionProcessorServiceImpl = new TransactionProcessorServiceImpl(strategies);
        FruitTransaction transaction1 =
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE,
                        "apple",
                        10);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                5);
        transactions = List.of(transaction1, transaction2);
    }

    @Test
    void processTransaction_validInput_success() {
        Map<String, Integer> expected =
                transactionProcessorServiceImpl.processTransaction(transactions);
        Map<String, Integer> actual = Storage.getFruits();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void processTransaction_invalidOperationType() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.RETURN;
        String fruit = "apple";
        int quantity = 10;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
        transactionProcessorServiceImpl.processSingleTransaction(transaction);
        Assertions.assertNull(Storage.getFruits().get(fruit));
    }

    @Test
    void processTransaction_emptyTransactionList_ok() {
        List<FruitTransaction> transactionList = new ArrayList<>();
        Map<String, Integer> initialStorageState = new HashMap<>(Storage.getFruits());
        Map<String, Integer> result =
                transactionProcessorServiceImpl.processTransaction(transactionList);
        Assertions.assertEquals(initialStorageState,
                result, "Expected the returned map to be equal to the "
                        + "initial state of the Storage");
    }

    @Test
    void processTransaction_nullTransactionList_noException() {
        Assertions.assertDoesNotThrow(() ->
                        transactionProcessorServiceImpl.processTransaction(null),
                "Expected processTransaction to not throw an exception "
                        + "when transactions list is null");
    }

    @Test
    void processSingleTransaction_nullOperationStrategy_noException() {
        FruitTransaction transaction = new FruitTransaction(null,
                "apple",
                10);
        Assertions.assertDoesNotThrow(() ->
                        transactionProcessorServiceImpl.processSingleTransaction(transaction),
                "Expected processSingleTransaction to not throw "
                        + "an exception when operation strategy is null");
    }
}
