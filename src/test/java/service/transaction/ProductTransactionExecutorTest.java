package service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dao.Dao;
import dao.StorageDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.transaction.strategy.ProductTransactionStrategy;
import service.transaction.strategy.TransactionStrategy;
import service.transaction.strategy.type.BalanceTransaction;
import service.transaction.strategy.type.TransactionHandler;

public class ProductTransactionExecutorTest {
    private static TransactionStrategy transactionStrategy;
    private static Dao dao;
    private static TransactionExecutor transactionExecutor;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.OperationType, TransactionHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.OperationType.BALANCE, new BalanceTransaction());
        transactionStrategy = new ProductTransactionStrategy(handlers);
        dao = new StorageDao();
        transactionExecutor = new ProductTransactionExecutor(transactionStrategy, dao);
    }

    @BeforeEach
    void setUp() {
        dao.updateStock(new HashMap<>());
    }

    @Test
    void validCase_fullData() {
        FruitTransaction banana = new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                "banana", 120);
        FruitTransaction apple = new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                "apple", 10);
        List<FruitTransaction> transactions = List.of(banana, apple);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 120);
        expected.put("apple", 10);
        transactionExecutor.execute(transactions);
        Map<String, Integer> actual = dao.getStock();
        assertEquals(expected, actual);
    }

    @Test
    void validCase_emptyData() {
        List<FruitTransaction> transactions = List.of();
        Map<String, Integer> expected = new HashMap<>();
        transactionExecutor.execute(transactions);
        Map<String, Integer> actual = dao.getStock();
        assertEquals(expected, actual);
    }

    @Test
    void invalidCase_nullData() {
        assertThrows(NullPointerException.class, () -> transactionExecutor.execute(null));
    }

    @AfterAll
    static void afterAll() {
        dao.updateStock(new HashMap<>());
    }
}
