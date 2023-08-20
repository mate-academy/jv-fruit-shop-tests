package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exception.StorageException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private static TransactionService transactionService;

    @BeforeAll
    static void beforeAll() {
        transactionService = new TransactionServiceImpl(new OperationStrategyImpl());
    }

    @BeforeEach
    void setUp() {
        FruitStorage.fruits.clear();
    }

    @Test
    void processTransactions_TooMuchForPurchase_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", new BigDecimal(20)),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", new BigDecimal(100)));
        assertThrows(StorageException.class, () ->
                transactionService.processTransactions(transactions));
    }

    @Test
    void processTransactions_NoSuchFruitForPurchase_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", new BigDecimal(20)),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "apple", new BigDecimal(100)));
        assertThrows(StorageException.class, () ->
                transactionService.processTransactions(transactions));
    }

    @Test
    void processTransactions_NoSuchFruitForReturn_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", new BigDecimal(20)),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "apple", new BigDecimal(100)));
        assertThrows(StorageException.class, () ->
                transactionService.processTransactions(transactions));
    }

    @Test
    void processTransactions_ValidData_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", new BigDecimal(20)),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana", new BigDecimal(100)),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", new BigDecimal(13)));
        transactionService.processTransactions(transactions);
        Map<String, BigDecimal> expected = Map.of("banana", new BigDecimal(107));
        Map<String, BigDecimal> actual = FruitStorage.fruits;
        assertEquals(expected, FruitStorage.fruits);
    }
}
