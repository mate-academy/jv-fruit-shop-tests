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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void constructor_nullParameters_NotOk() {
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
    void updateStorage_parameterIsNull_NotOk() {
        expectedStorageSize = Storage.storage.size();
        List<FruitTransaction> nullTransactionList = null;
        Throwable exception = assertThrows(NullPointerException.class,
                () -> storeService.updateStorage(nullTransactionList));
        assertEquals("Transaction list is null", exception.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @Test
    void updateStorage_nullElementInTransactionList_NotOk() {
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
    void update_transactionListIsEmpty_NotOk() {
        expectedStorageSize = Storage.storage.size();
        List<FruitTransaction> emptyList = new ArrayList<>();
        Throwable exception1 = assertThrows(RuntimeException.class,
                () -> storeService.updateStorage(emptyList));
        assertEquals("Transaction list is empty", exception1.getMessage());
        emptyList.add(new FruitTransaction());
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @Test
    void update_negativeTotalBalance_NotOk() {
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

    @Test
    void updateStorage_newArticles_Ok() {
        Storage.storage.put("apple", 0);
        Storage.storage.put("banana", 0);
        Storage.storage.put("pear", 0);
        Storage.storage.put("lemon", 0);
        Storage.storage.put("orange", 0);
        FruitTransaction.Operation balance = FruitTransaction.Operation.BALANCE;
        Map<String, Integer> fruitMap = new HashMap<>();
        fruitMap.put("apple", 50);
        fruitMap.put("banana", 5345);
        fruitMap.put("pear", 434);
        fruitMap.put("orange", 675);
        fruitMap.put("lemon", 300);
        List<FruitTransaction> transactions = new ArrayList<>();
        for (Map.Entry<String, Integer> fruitQuantity : fruitMap.entrySet()) {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(balance);
            transaction.setFruit(fruitQuantity.getKey());
            transaction.setQuantity(fruitQuantity.getValue());
            transactions.add(transaction);
        }
        storeService.updateStorage(transactions);
        for (Map.Entry<String, Integer> fruitQuantity : fruitMap.entrySet()) {
            String expectedFruit = fruitQuantity.getKey();
            int expectedValue = fruitQuantity.getValue();
            assertTrue(Storage.storage.containsKey(expectedFruit));
            assertEquals(expectedValue, Storage.storage.get(expectedFruit));
        }
    }

    @Test
    void updateStorage_updatedStorage_Ok() {
        Storage.storage.put("apple", 0);
        Storage.storage.put("banana", 0);
        Storage.storage.put("pear", 0);
        Storage.storage.put("lemon", 0);
        Storage.storage.put("orange", 0);
        FruitTransaction.Operation balance = FruitTransaction.Operation.BALANCE;
        Map<String, Integer> fruitMap1 = new HashMap<>();
        fruitMap1.put("apple", 50);
        fruitMap1.put("banana", 5345);
        fruitMap1.put("pear", 434);
        fruitMap1.put("orange", 675);
        fruitMap1.put("lemon", 300);
        List<FruitTransaction> transactions1 = new ArrayList<>();
        for (Map.Entry<String, Integer> fruitQuantity : fruitMap1.entrySet()) {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(balance);
            transaction.setFruit(fruitQuantity.getKey());
            transaction.setQuantity(fruitQuantity.getValue());
            transactions1.add(transaction);
        }
        storeService.updateStorage(transactions1);
        for (Map.Entry<String, Integer> fruitQuantity : fruitMap1.entrySet()) {
            String expectedFruit = fruitQuantity.getKey();
            int expectedValue = fruitQuantity.getValue();
            assertTrue(Storage.storage.containsKey(expectedFruit));
            assertEquals(expectedValue, Storage.storage.get(expectedFruit));
        }
        Map<String, Integer> fruitMap2 = new HashMap<>();
        fruitMap2.put("apple", 345);
        fruitMap2.put("banana", 5);
        fruitMap2.put("pear", 0);
        fruitMap2.put("orange", 798);
        fruitMap2.put("lemon", 54);
        List<FruitTransaction> transactions2 = new ArrayList<>();
        for (Map.Entry<String, Integer> fruitQuantity : fruitMap2.entrySet()) {
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(balance);
            transaction.setFruit(fruitQuantity.getKey());
            transaction.setQuantity(fruitQuantity.getValue());
            transactions2.add(transaction);
        }
        storeService.updateStorage(transactions2);
        for (Map.Entry<String, Integer> fruitQuantity : fruitMap2.entrySet()) {
            String expectedFruit = fruitQuantity.getKey();
            int expectedValue = fruitQuantity.getValue();
            assertTrue(Storage.storage.containsKey(expectedFruit));
            assertEquals(expectedValue, Storage.storage.get(expectedFruit));
        }
    }

    @Test
    void createReport_storageContainsArticles_Ok() {
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
        for (Map.Entry<String, Integer> entry : Storage.storage.entrySet()) {
            String expectedLine = line
                    .append(entry.getKey())
                    .append(fieldSeparator)
                    .append(entry.getValue())
                    .toString();
            assertTrue(report.contains(expectedLine));
            line.setLength(0);
        }
    }

    @Test
    void createReport_storageIsEmpty_NotOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> storeService.createReport());
        assertEquals("Storage is empty", exception.getMessage());
    }
}
