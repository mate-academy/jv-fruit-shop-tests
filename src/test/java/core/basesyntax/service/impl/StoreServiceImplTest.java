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

class StoreServiceImplTest {
    private static final ArticleDao ARTICLE_DAO = new ArticleDaoImpl();
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
        assertEquals("Parameters can't be null, but:"
                + "\narticleDao = " + nullArticleDao
                + "\ntransactionStrategy = " + nullStrategy, exception1.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
        Throwable exception2 = assertThrows(IllegalArgumentException.class,
                () -> storeService = new StoreServiceImpl(nullArticleDao, transactionStrategy));
        assertEquals("Parameters can't be null, but:"
                + "\narticleDao = " + nullArticleDao
                + "\ntransactionStrategy = " + transactionStrategy, exception2.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
        Throwable exception3 = assertThrows(IllegalArgumentException.class,
                () -> storeService = new StoreServiceImpl(ARTICLE_DAO, nullStrategy));
        assertEquals("Parameters can't be null, but:"
                + "\narticleDao = " + ARTICLE_DAO
                + "\ntransactionStrategy = " + nullStrategy, exception3.getMessage());
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

    @Test
    void updateStorage_nullElementInTransactionList_notOk() {
        expectedStorageSize = Storage.storage.size();
        List<FruitTransaction> transactionListNullElement = new ArrayList<>();
        transactionListNullElement.add(null);
        Throwable exception1 = assertThrows(NullPointerException.class,
                () -> storeService.updateStorage(transactionListNullElement));
        assertEquals("Transaction list contain null element", exception1.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
        transactionListNullElement.add(new FruitTransaction());
        Throwable exception2 = assertThrows(NullPointerException.class,
                () -> storeService.updateStorage(transactionListNullElement));
        assertEquals("Transaction list contain null element", exception2.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
        transactionListNullElement.add(null);
        transactionListNullElement.add(new FruitTransaction());
        transactionListNullElement.add(new FruitTransaction());
        transactionListNullElement.add(new FruitTransaction());
        Throwable exception3 = assertThrows(NullPointerException.class,
                () -> storeService.updateStorage(transactionListNullElement));
        assertEquals("Transaction list contain null element", exception3.getMessage());
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
        Storage.storage.put("apple", 0);
        Storage.storage.put("banana", 0);
        Storage.storage.put("pear", 0);
        Storage.storage.put("lemon", 0);
        Storage.storage.put("orange", 0);
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
        assertThrows(RuntimeException.class, () -> storeService.updateStorage(transactions));
        assertEquals(expectedStorageSize, Storage.storage.size());
        fruitMap.put("banana", 5345);
        for (Map.Entry<String, Integer> fruitQuantity : fruitMap.entrySet()) {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(balance);
            transaction.setFruit(fruitQuantity.getKey());
            transaction.setQuantity(fruitQuantity.getValue());
            transactions.add(transaction);
        }
        assertThrows(RuntimeException.class, () -> storeService.updateStorage(transactions));
        assertEquals(expectedStorageSize, Storage.storage.size());
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
    }

    @ParameterizedTest
    @MethodSource
    void updateStorage_storageContainsArticles_ok(String fruit, int quantity) {
        Storage.storage.put("apple", 0);
        Storage.storage.put("banana", 0);
        Storage.storage.put("pear", 0);
        Storage.storage.put("lemon", 0);
        Storage.storage.put("orange", 0);
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
        Storage.storage.put("apple", 100);
        Storage.storage.put("lemon", 53);
        Storage.storage.put("orange", 28);
        Storage.storage.put("banana", 100);
        Storage.storage.put("peach", 100);
        Storage.storage.put("pear", 100);
        List<String> report = storeService.createReport();
        String expectedLineWithColumnNames = "fruit,quantity";
        int expectedRowWithColumnNamesIndex = 0;
        int expectedReportLength = Storage.storage.size() + 1;
        assertEquals(report.get(expectedRowWithColumnNamesIndex), expectedLineWithColumnNames);
        assertEquals(expectedReportLength, report.size());
        String fieldSeparator = ",";
        StringBuilder line = new StringBuilder();
        String expectedLine = line
                .append(fruit)
                .append(fieldSeparator)
                .append(quantity)
                .toString();
        assertTrue(report.contains(expectedLine));
        line.setLength(0);

    }

    private static Stream<Arguments> createReport_storageContainsArticles_ok() {
        return Stream.of(
                Arguments.of("apple", 100),
                Arguments.of("lemon", 53),
                Arguments.of("orange", 28),
                Arguments.of("banana", 100),
                Arguments.of("peach", 100),
                Arguments.of("pear", 100)
        );
    }

    @Test
    void createReport_storageIsEmpty_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> storeService.createReport());
        assertEquals("Storage is empty", exception.getMessage());
    }
}
