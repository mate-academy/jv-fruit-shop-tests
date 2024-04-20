package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessorService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorServiceImplTest {
    private Map<String, Integer> fruitCounts;
    private List<FruitTransaction> transactions = new ArrayList<>();
    private TransactionProcessorService transactionProcessorService;
    private TransactionProcessorServiceImpl singleTransaction;

    private Map<FruitTransaction.Operation, OperationStrategy> strategies;

    @BeforeEach
    void setUp() {
        fruitCounts = new HashMap<>();
        strategies = new HashMap<>();
        singleTransaction = new TransactionProcessorServiceImpl(strategies);
        transactionProcessorService = new TransactionProcessorServiceImpl(strategies);
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
                transactionProcessorService.processTransaction(transactions);
        Map<String, Integer> actual = Storage.getFruits();
        assertEquals(expected, actual);
    }

    @Test
    void processTransaction_invalidOperationType() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.RETURN;
        String fruit = "apple";
        int quantity = 10;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);

        singleTransaction.processSingleTransaction(transaction);
        Assertions.assertNull(Storage.getFruits().get(fruit));
    }

    @Test
    void processTransaction_emptyTransactionList_ok() {
        List<FruitTransaction> transactionList = new ArrayList<>();
        Map<String, Integer> initialStorageState = new HashMap<>(Storage.getFruits());
        Map<String, Integer> result =
                transactionProcessorService.processTransaction(transactionList);
        assertEquals(initialStorageState,
                result, "Expected the returned map to be equal to the "
                        + "initial state of the Storage");
    }

    @Test
    void processTransaction_nullTransactionList_noException() {
        Assertions.assertDoesNotThrow(() ->
                        transactionProcessorService.processTransaction(null),
                "Expected processTransaction to not throw an exception "
                        + "when transactions list is null");
    }

    @Test
    void processSingleTransaction_nullOperationStrategy_noException() {
        FruitTransaction transaction = new FruitTransaction(null,
                "apple",
                10);
        Assertions.assertDoesNotThrow(() ->
                        singleTransaction.processSingleTransaction(transaction),
                "Expected processSingleTransaction to not throw "
                        + "an exception when operation strategy is null");
    }

    @Test
    void processSingleTransaction_strategyApplyCalledWithCorrectParameters() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        String fruit = "apple";
        int quantity = 10;
        FruitTransaction transaction = new FruitTransaction(operation, fruit,
                quantity);
        OperationStrategy testStrategy = new OperationStrategy() {
            @Override
        public void apply(Map<String, Integer> fruitCountsDifferent,
                                  String fruit, int quantity) {
                    fruitCounts.put(transaction.getFruit(), transaction.getQuantity());
                }
            };
        strategies.put(operation, testStrategy);

        singleTransaction.processSingleTransaction(transaction);

        assertEquals(quantity, fruitCounts.get(fruit));
    }
}
