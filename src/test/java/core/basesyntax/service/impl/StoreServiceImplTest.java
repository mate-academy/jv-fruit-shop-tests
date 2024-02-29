package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.ArticleDao;
import core.basesyntax.dao.ArticleDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StoreService;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import core.basesyntax.strategy.handler.BalanceTransactionHandler;
import core.basesyntax.strategy.handler.TransactionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

class StoreServiceImplTest {
    private static final ArticleDao ARTICLE_DAO = new ArticleDaoImpl();
    private static final int LINE_WITH_COLUMN_NAMES_INDEX = 0;
    private static final int REPORT_LENGTH = 7;
    private static final int DEFAULT_LENGTH = 0;
    private static final String LINE_WITH_COLUMN_NAMES = "fruit,quantity";
    private static final String FIELD_SEPARATOR = ",";
    private static TransactionStrategy transactionStrategy;
    private static StoreService storeService;
    private int expectedStorageSize;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, TransactionHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceTransactionHandler());
        transactionStrategy = new TransactionStrategyImpl(strategyMap);
        storeService = new StoreServiceImpl(ARTICLE_DAO, transactionStrategy);
    }

    @BeforeEach
    void beforeEach() {
        Storage.storage.clear();
    }

    @Test
    void constructor_nullParameters_notOk() {
        expectedStorageSize = Storage.storage.size();
        TransactionStrategy nullStrategy = null;
        ArticleDao nullArticleDao = null;
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                () -> storeService = new StoreServiceImpl(nullArticleDao, nullStrategy));
        assertEquals("""
                Parameters can't be null, but:
                articleDao = %s
                transactionStrategy = %s""".formatted(nullArticleDao, nullStrategy),
                exception1.getMessage());
        Throwable exception2 = assertThrows(IllegalArgumentException.class,
                () -> storeService = new StoreServiceImpl(nullArticleDao, transactionStrategy));
        assertEquals(expectedStorageSize, Storage.storage.size());
        assertEquals("""
                Parameters can't be null, but:
                articleDao = %s
                transactionStrategy = %s""".formatted(nullArticleDao, transactionStrategy),
                exception2.getMessage());
        Throwable exception3 = assertThrows(IllegalArgumentException.class,
                () -> storeService = new StoreServiceImpl(ARTICLE_DAO, nullStrategy));
        assertEquals(expectedStorageSize, Storage.storage.size());
        assertEquals("""
                Parameters can't be null, but:
                articleDao = %s
                transactionStrategy = %s""".formatted(ARTICLE_DAO, nullStrategy),
                exception3.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @Test
    void updateStorage_parameterIsNull_notOk() {
        expectedStorageSize = Storage.storage.size();
        List<FruitTransaction> nullTransactionList = null;
        Throwable exception = assertThrows(NullPointerException.class,
                () -> storeService.updateStorage(nullTransactionList));
        assertEquals("Transaction list is null", exception.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @ParameterizedTest
    @NullSource
    void updateStorage_nullElementInTransactionList_notOk(FruitTransaction transaction) {
        expectedStorageSize = Storage.storage.size();
        List<FruitTransaction> transactionListNullElement = new ArrayList<>();
        transactionListNullElement.add(transaction);
        Throwable exception = assertThrows(NullPointerException.class,
                () -> storeService.updateStorage(transactionListNullElement));
        assertEquals("Transaction list contain null element", exception.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @Test
    void update_transactionListIsEmpty_notOk() {
        expectedStorageSize = Storage.storage.size();
        List<FruitTransaction> emptyList = new ArrayList<>();
        Throwable exception1 = assertThrows(RuntimeException.class,
                () -> storeService.updateStorage(emptyList));
        assertEquals("Transaction list is empty", exception1.getMessage());
        emptyList.add(new FruitTransaction());
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @Test
    void update_negativeTotalBalance_notOk() {
        Storage.storage.putAll(Map.of(
                "apple", 0,
                "banana", 0,
                "pear", 0,
                "lemon", 0,
                "orange", 0
                ));
        expectedStorageSize = Storage.storage.size();
        FruitTransaction.Operation balance = FruitTransaction.Operation.BALANCE;
        Map<String, Integer> fruitMap = new HashMap<>();
        fruitMap.put("apple", -50);
        List<FruitTransaction> transactions = new ArrayList<>();
        for (Map.Entry<String, Integer> fruitQuantity : fruitMap.entrySet()) {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(balance);
            transaction.setFruit(fruitQuantity.getKey());
            transaction.setQuantity(fruitQuantity.getValue());
            transactions.add(transaction);
        }
        fruitMap.put("banana", 5345);
        for (Map.Entry<String, Integer> fruitQuantity : fruitMap.entrySet()) {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(balance);
            transaction.setFruit(fruitQuantity.getKey());
            transaction.setQuantity(fruitQuantity.getValue());
            transactions.add(transaction);
        }
        Storage.storage.put("pear", -3445);
        Storage.storage.put("lemon", 534);
        Storage.storage.put("orange", 2);
        for (Map.Entry<String, Integer> fruitQuantity : fruitMap.entrySet()) {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(balance);
            transaction.setFruit(fruitQuantity.getKey());
            transaction.setQuantity(fruitQuantity.getValue());
            transactions.add(transaction);
        }
        assertThrows(RuntimeException.class, () -> storeService.updateStorage(transactions));
        assertEquals(expectedStorageSize, Storage.storage.size());
        assertThrows(RuntimeException.class, () -> storeService.updateStorage(transactions));
        assertEquals(expectedStorageSize, Storage.storage.size());
        assertThrows(RuntimeException.class, () -> storeService.updateStorage(transactions));
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @ParameterizedTest
    @MethodSource
    void updateStorage_storageContainsArticles_ok(String fruit, int quantity) {
        Storage.storage.putAll(Map.of(
                "apple", 0,
                "banana", 0,
                "pear", 0,
                "lemon", 0,
                "orange", 0
        ));
        FruitTransaction.Operation balance = FruitTransaction.Operation.BALANCE;
        final List<FruitTransaction> transactions = new ArrayList<>();
        final FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(balance);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        transactions.add(transaction);
        storeService.updateStorage(transactions);
        assertTrue(Storage.storage.containsKey(fruit));
        assertEquals(quantity, Storage.storage.get(fruit));
    }

    private static Stream<Arguments> updateStorage_storageContainsArticles_ok() {
        return Stream.of(
                Arguments.of("apple", 50),
                Arguments.of("lemon", 53),
                Arguments.of("orange",675),
                Arguments.of("banana", 5345),
                Arguments.of("pear", 434)
        );
    }

    @ParameterizedTest
    @MethodSource
    void createReport_storageContainsArticles_ok(String fruit, int quantity) {
        Storage.storage.putAll(Map.of(
                "apple", 100,
                "banana", 53,
                "pear", 100,
                "lemon", 53,
                "orange", 28,
                "peach", 100
        ));
        List<String> report = storeService.createReport();
        assertEquals(report.get(LINE_WITH_COLUMN_NAMES_INDEX), LINE_WITH_COLUMN_NAMES);
        assertEquals(REPORT_LENGTH, report.size());
        StringBuilder line = new StringBuilder();
        String expectedLine = line
                .append(fruit)
                .append(FIELD_SEPARATOR)
                .append(quantity)
                .toString();
        assertTrue(report.contains(expectedLine));
        line.setLength(DEFAULT_LENGTH);
    }

    private static Stream<Arguments> createReport_storageContainsArticles_ok() {
        return Stream.of(
                Arguments.of("apple", 100),
                Arguments.of("banana", 53),
                Arguments.of("pear", 100),
                Arguments.of("lemon", 53),
                Arguments.of("orange", 28),
                Arguments.of("peach", 100)
        );
    }

    @Test
    void createReport_storageIsEmpty_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> storeService.createReport());
        assertEquals("Storage is empty", exception.getMessage());
    }
}
