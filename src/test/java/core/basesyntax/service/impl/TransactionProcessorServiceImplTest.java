package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceStrategy;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseStrategy;
import core.basesyntax.strategy.SupplyStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorServiceImplTest {
    private static Map<String, Integer> fruitCounts;
    private List<FruitTransaction> transactions = new ArrayList<>();
    private TransactionProcessorServiceImpl transactionProcessorService;
    private TransactionProcessorServiceImpl singleTransaction;
    private Map<FruitTransaction.Operation, OperationStrategy> strategies;
    private OperationStrategy customStrategy = new OperationStrategy() {
        @Override
        public void apply(Map<String, Integer> fruitCounts, String fruit, int quantity) {
            TransactionProcessorServiceImplTest.fruitCounts.put(fruit, quantity);
        }
    };

    @BeforeEach
    void setUp() {
        initializeTransactions();
        initializeAndPopulateStorage();
        initializeStrategies();
        initializeTransactionProcessors();
    }

    void initializeStrategies() {
        strategies = new HashMap<>();
        strategies.put(FruitTransaction.Operation.BALANCE, new BalanceStrategy());
        strategies.put(FruitTransaction.Operation.SUPPLY, new SupplyStrategy());
        strategies.put(FruitTransaction.Operation.PURCHASE, new PurchaseStrategy());
    }

    void initializeTransactionProcessors() {
        transactionProcessorService = new TransactionProcessorServiceImpl(strategies);
        transactionProcessorService.setFruitCounts(fruitCounts);
        singleTransaction = new TransactionProcessorServiceImpl(strategies);
        singleTransaction.setFruitCounts(fruitCounts);
    }

    void initializeTransactions() {
        FruitTransaction transaction =
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE,
                        "apple",
                        10);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                5);
        transactions = List.of(transaction, transaction2);
    }

    void initializeAndPopulateStorage() {
        fruitCounts = new HashMap<>();
        for (FruitTransaction singleTransaction : transactions) {
            Storage.fruits.put(singleTransaction.getFruit(),
                    singleTransaction.getQuantity());
        }
    }

    @Test
    void processTransaction_validInput_success() {
        Map<String, Integer> expected =
                transactionProcessorService.processTransactions(transactions);
        Map<String, Integer> actual = Storage.getFruits();
        assertEquals(expected, actual);
    }

    @Test
    void processTransaction_emptyTransactionList_ok() {
        List<FruitTransaction> transactionList = new ArrayList<>();
        Map<String, Integer> initialStorageState = new HashMap<>(Storage.getFruits());
        Map<String, Integer> result =
                transactionProcessorService.processTransactions(transactionList);
        assertEquals(initialStorageState,
                result, "Expected the returned map to be equal to the "
                        + "initial state of the Storage");
    }

    @Test
    void processTransaction_nullTransactionList_noException() {
        Assertions.assertDoesNotThrow(() ->
                        transactionProcessorService.processTransactions(null),
                "Expected processTransactions to not throw an exception "
                        + "when transactions list is null");
    }

    @Test
    void processSingleTransaction_invalidOperationType() {
        FruitTransaction.Operation invalidOperation =
                FruitTransaction.Operation.RETURN;
        FruitTransaction transaction = new FruitTransaction(invalidOperation,
                "apple", 10);

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            singleTransaction.processSingleTransaction(transaction);
        });

        String expectedMessage = "Unknown operation type: " + invalidOperation.getCode();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void processSingleTransaction_strategyApplyCalledWithCorrectParameters() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        String fruit = "apple";
        int quantity = 10;
        FruitTransaction transaction = new FruitTransaction(operation, fruit,
                quantity);

        strategies.put(operation, customStrategy);

        singleTransaction.processSingleTransaction(transaction);

        assertEquals(quantity, fruitCounts.get(fruit));
    }
}
