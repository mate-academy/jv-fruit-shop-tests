package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.StorageTransaction;
import core.basesyntax.service.CalculatorService;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.TransactionHandler;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.impl.BalanceTransactionHandler;
import core.basesyntax.strategy.impl.PurchaseTransactionHandler;
import core.basesyntax.strategy.impl.ReturnTransactionHandler;
import core.basesyntax.strategy.impl.SupplyTransactionHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculatorServiceImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int BANANA_QUANTITY = 152;
    private static final int APPLE_QUANTITY = 90;
    private static final Map<StorageTransaction.Operation,
            TransactionHandler> operations = new HashMap<>();
    private static final List<StorageTransaction> EMPTY_LIST = Collections.emptyList();
    private static List<StorageTransaction> transactions;
    private static CalculatorService calculatorService;
    private static Strategy strategy;

    @BeforeClass
    public static void setUp() {
        operations.put(StorageTransaction.Operation.BALANCE, new BalanceTransactionHandler());
        operations.put(StorageTransaction.Operation.RETURN, new ReturnTransactionHandler());
        operations.put(StorageTransaction.Operation.SUPPLY, new SupplyTransactionHandler());
        operations.put(StorageTransaction.Operation.PURCHASE, new PurchaseTransactionHandler());
        strategy = new TransactionStrategy(operations);
        calculatorService = new CalculatorServiceImpl(strategy);
        transactions = new ArrayList<>();
        transactions.add(new StorageTransaction(
                StorageTransaction.Operation.BALANCE, BANANA, 20));
        transactions.add(new StorageTransaction(
                StorageTransaction.Operation.BALANCE, APPLE, 100));
        transactions.add(new StorageTransaction(
                StorageTransaction.Operation.SUPPLY, BANANA, 100));
        transactions.add(new StorageTransaction(
                StorageTransaction.Operation.PURCHASE, BANANA, 13));
        transactions.add(new StorageTransaction(
                StorageTransaction.Operation.RETURN, APPLE, 10));
        transactions.add(new StorageTransaction(
                StorageTransaction.Operation.PURCHASE, APPLE, 20));
        transactions.add(new StorageTransaction(
                StorageTransaction.Operation.PURCHASE, BANANA, 5));
        transactions.add(new StorageTransaction(
                StorageTransaction.Operation.SUPPLY, BANANA, 50));
    }

    @Test(expected = RuntimeException.class)
    public void calculate_methodParameterIsEmpty_notOk() {
        calculatorService.calculate(EMPTY_LIST);
        fail("You should throw RuntimeException when method parameter is empty");
    }

    @Test
    public void calculate_getCorrectData_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(BANANA, BANANA_QUANTITY);
        expected.put(APPLE, APPLE_QUANTITY);
        calculatorService.calculate(transactions);
        assertEquals("Expected size: " + expected.size(),
                expected.size(), Storage.storage.size());
        assertEquals(expected, Storage.storage);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}
