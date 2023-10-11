package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.Operation;
import core.basesyntax.service.TransactionService;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.strategy.BalanceOperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationStrategy;
import core.basesyntax.strategy.ReturnOperationStrategy;
import core.basesyntax.strategy.SupplyOperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionServiceImplTest {

    private static final Map<Operation, OperationHandler> operationStrategyMap = new HashMap<>();
    private static TransactionService transactionService = null;

    @BeforeEach
    public void cleanStorage() {
        FruitStorage.fruitStorage.clear();
    }

    @BeforeEach
    public void setUp() throws Exception {
        operationStrategyMap.put(Operation.BALANCE, new BalanceOperationStrategy());
        operationStrategyMap.put(Operation.PURCHASE, new PurchaseOperationStrategy());
        operationStrategyMap.put(Operation.SUPPLY, new SupplyOperationStrategy());
        operationStrategyMap.put(Operation.RETURN, new ReturnOperationStrategy());

        transactionService = new TransactionServiceImpl(operationStrategyMap);

        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void handleTransactionsTest_OK() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        transactions.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        transactions.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        transactions.add(new FruitTransaction(Operation.PURCHASE, "banana", 13));

        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 107);
        expected.put("apple", 100);

        transactionService.handleTransactions(transactions);
        Map<String, Integer> actual = FruitStorage.fruitStorage;

        assertEquals(expected, actual);
        transactions.clear();
        expected.clear();
        actual.clear();
        FruitStorage.fruitStorage.clear();

        transactions.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        transactions.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        transactions.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        transactions.add(new FruitTransaction(Operation.PURCHASE, "banana", 0));

        expected.put("banana", 120);
        expected.put("apple", 100);

        transactionService.handleTransactions(transactions);
        actual = FruitStorage.fruitStorage;

        assertEquals(expected, actual);
    }

    @Test
    public void handleTransactionsTest_IncorrectResult_notOK() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        transactions.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        transactions.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        transactions.add(new FruitTransaction(Operation.PURCHASE, "banana", 13));

        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 105);
        expected.put("apple", 100);

        transactionService.handleTransactions(transactions);
        Map<String, Integer> actual = FruitStorage.fruitStorage;

        assertNotEquals(expected, actual);
    }

    @Test
    public void handleTransactionsTest_NegativePurchase_notOK() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        transactions.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        transactions.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        transactions.add(new FruitTransaction(Operation.PURCHASE, "banana", -13));

        assertThrows(RuntimeException.class, () -> {
            transactionService.handleTransactions(transactions);
        });
    }

    @Test
    public void handleTransactionsTest_NullTransactions_notOK() {
        assertThrows(RuntimeException.class, () -> {
            transactionService.handleTransactions(null);
        });
    }

    @Test
    public void handleTransactionsTest_EmptyTransactions_notOK() {
        assertThrows(RuntimeException.class, () -> {
            transactionService.handleTransactions(List.of());
        });
    }
}
