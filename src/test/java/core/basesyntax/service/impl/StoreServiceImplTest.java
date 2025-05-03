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
    private static final ArticleDao articleDao = new ArticleDaoImpl();
    private static TransactionStrategy transactionStrategy;
    private static StoreService storeService;
    private static final int LINE_WITH_COLUMN_NAMES_INDEX = 0;
    private static final int REPORT_LENGTH = 7;
    private static final int DEFAULT_LENGTH = 0;
    private static final String FIELD_SEPARATOR = ",";
    private static final String LINE_WITH_COLUMN_NAMES = "fruit,quantity";
    private static final String EMPTY_STORAGE_MESSAGE = "Storage is empty";
    private static final String NULL_LIST_MESSAGE = "Transaction list is null";
    private static final String LIST_CONTAIN_NULL_MESSAGE = "Transaction list contain null element";
    private static final String EMPTY_LIST_MESSAGE = "Transaction list is empty";
    private static final String NULL_PARAMETERS_MESSAGE = """
                Parameters can't be null, but:
                articleDao = %s
                transactionStrategy = %s""";
    private static final String NEGATIVE_BALANCE_MESSAGE = """
                        Incorrect data - the balance of %s less than 0
                        %s: %s""";
    private int expectedStorageSize;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, TransactionHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceTransactionHandler());
        transactionStrategy = new TransactionStrategyImpl(strategyMap);
        storeService = new StoreServiceImpl(articleDao, transactionStrategy);
    }

    @BeforeEach
    void beforeEach() {
        Storage.storage.clear();
    }

    @ParameterizedTest
    @MethodSource
    void constructor_nullParameters_notOk(ArticleDao articleDao, TransactionStrategy strategy) {
        expectedStorageSize = Storage.storage.size();
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                () -> storeService = new StoreServiceImpl(articleDao, strategy));
        assertEquals(NULL_PARAMETERS_MESSAGE.formatted(articleDao, strategy),
                exception1.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    static Stream<Arguments> constructor_nullParameters_notOk() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, transactionStrategy),
                Arguments.of(articleDao, null)
        );
    }

    @Test
    void updateStorage_parameterIsNull_notOk() {
        expectedStorageSize = Storage.storage.size();
        List<FruitTransaction> nullTransactionList = null;
        Throwable exception = assertThrows(NullPointerException.class,
                () -> storeService.updateStorage(nullTransactionList));
        assertEquals(NULL_LIST_MESSAGE, exception.getMessage());
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
        assertEquals(LIST_CONTAIN_NULL_MESSAGE, exception.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @Test
    void update_transactionListIsEmpty_notOk() {
        expectedStorageSize = Storage.storage.size();
        List<FruitTransaction> emptyList = new ArrayList<>();
        Throwable exception1 = assertThrows(RuntimeException.class,
                () -> storeService.updateStorage(emptyList));
        assertEquals(EMPTY_LIST_MESSAGE, exception1.getMessage());
        emptyList.add(new FruitTransaction());
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @ParameterizedTest
    @MethodSource
    void update_negativeTotalBalance_notOk(Map<String, Integer> fruitMap,
                                           String expectedArticle) {
        Storage.storage.putAll(Map.of(
                "apple", 0,
                "banana", 0,
                "pear", 0,
                "lemon", 0,
                "orange", 0)
        );
        Map<String, Integer> expectedMap = Map.copyOf(Storage.storage);
        FruitTransaction.Operation balance = FruitTransaction.Operation.BALANCE;
        List<FruitTransaction> transactions = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : fruitMap.entrySet()) {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(balance);
            transaction.setFruit(entry.getKey());
            transaction.setQuantity(entry.getValue());
            transactions.add(transaction);
        }
        Exception exception = assertThrows(RuntimeException.class,
                () -> storeService.updateStorage(transactions));
        assertEquals(NEGATIVE_BALANCE_MESSAGE.formatted(expectedArticle, expectedArticle,
                fruitMap.get(expectedArticle)), exception.getMessage());
        assertEquals(expectedMap, Storage.storage);
    }

    static Stream<Arguments> update_negativeTotalBalance_notOk() {
        return Stream.of(
                Arguments.of(Map.of(
                        "apple", -50), "apple"),
                Arguments.of(Map.of(
                        "banana", 5345,
                        "pear", -3445), "pear"),
                Arguments.of(Map.of(
                        "apple", 50,
                        "banana", 5345,
                        "pear", 3445,
                        "lemon", -534,
                        "orange", 2), "lemon")
        );
    }

    @ParameterizedTest
    @MethodSource
    void updateStorage_storageContainsArticles_ok(String fruit, int quantity) {
        Storage.storage.putAll(Map.of(
                "apple", 0,
                "banana", 0,
                "pear", 0,
                "lemon", 0,
                "orange", 0)
        );
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
                "peach", 100)
        );
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
        assertEquals(EMPTY_STORAGE_MESSAGE, exception.getMessage());
    }
}
