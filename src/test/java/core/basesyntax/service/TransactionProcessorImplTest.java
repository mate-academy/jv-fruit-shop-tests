package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.QuantityCounterStrategy;
import core.basesyntax.strategy.QuantityCounterStrategyImpl;
import core.basesyntax.strategy.QuantityCountersMap;
import core.basesyntax.transaction.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static TransactionProcessor transactionProcessor;
    private static List<Transaction> validTransactionsList;
    private static Storage storage;
    private static Map<String, Integer> expectedMap;
    private static QuantityCounterStrategy quantityCounterStrategy;

    @BeforeAll
    static void beforeAll() {
        quantityCounterStrategy = new QuantityCounterStrategyImpl(new QuantityCountersMap()
                .getQuantityCountersMap());
        transactionProcessor = new TransactionProcessorImpl(quantityCounterStrategy);
        validTransactionsList = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setOperation(Transaction.Operation.BALANCE);
        transaction1.setProduct("banana");
        transaction1.setQuantity(20);
        validTransactionsList.add(transaction1);
        Transaction transaction2 = new Transaction();
        transaction2.setOperation(Transaction.Operation.PURCHASE);
        transaction2.setProduct("banana");
        transaction2.setQuantity(5);
        validTransactionsList.add(transaction2);
        Transaction transaction3 = new Transaction();
        transaction3.setOperation(Transaction.Operation.RETURN);
        transaction3.setProduct("banana");
        transaction3.setQuantity(1);
        validTransactionsList.add(transaction3);
        Transaction transaction4 = new Transaction();
        transaction4.setOperation(Transaction.Operation.SUPPLY);
        transaction4.setProduct("banana");
        transaction4.setQuantity(6);
        validTransactionsList.add(transaction4);
        Transaction transaction5 = new Transaction();
        transaction5.setOperation(Transaction.Operation.BALANCE);
        transaction5.setProduct("apple");
        transaction5.setQuantity(200);
        validTransactionsList.add(transaction5);
        Transaction transaction6 = new Transaction();
        transaction6.setOperation(Transaction.Operation.PURCHASE);
        transaction6.setProduct("apple");
        transaction6.setQuantity(4);
        validTransactionsList.add(transaction6);
        Transaction transaction7 = new Transaction();
        transaction7.setOperation(Transaction.Operation.RETURN);
        transaction7.setProduct("apple");
        transaction7.setQuantity(9);
        validTransactionsList.add(transaction7);
        Transaction transaction8 = new Transaction();
        transaction8.setOperation(Transaction.Operation.SUPPLY);
        transaction8.setProduct("apple");
        transaction8.setQuantity(5);
        validTransactionsList.add(transaction8);
        expectedMap = new HashMap<>();
        expectedMap.put("banana", 22);
        expectedMap.put("apple", 210);
    }

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void process_validInput_ok() {
        transactionProcessor.process(validTransactionsList, storage);
        Map<String, Integer> actual = storage.getProductsMap();
        assertEquals(expectedMap, actual,
                "Result Map should correctly represent data from input List");
    }

    @Test
    void process_nullList_notOk() {
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> transactionProcessor.process(null, storage),
                "If List is null, exception should be thrown");
        assertTrue(actual.getMessage().equals("List can't be null"));
    }

    @Test
    void process_emptyList_notOk() {
        transactionProcessor.process(new ArrayList<>(), storage);
        Map<String, Integer> actual = storage.getProductsMap();
        assertTrue(actual.isEmpty(), "If List is empty, Storage should be empty");
    }

    @Test
    void process_nullTransactionInList_notOk() {
        List<Transaction> nullTransactionInList = validTransactionsList;
        nullTransactionInList.add(null);
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> transactionProcessor.process(nullTransactionInList, storage),
                "If there is a null Transaction in the List, exception should be thrown");
        assertEquals("Transaction can't be null", actual.getMessage());
    }

    @Test
    void process_nullStorage_notOk() {
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> transactionProcessor.process(validTransactionsList, null),
                "If Storage is null, exception should be thrown");
        assertTrue(actual.getMessage().equals("Storage can't be null"));
    }

    @Test
    void process_notEmptyStorage_ok() {
        Map<String, Integer> map = new HashMap<>();
        map.put("orange", 5);
        map.put("lemon", 23);
        map.put("berry", 2);
        storage.setProductsMap(map);
        transactionProcessor.process(validTransactionsList, storage);
        Map<String, Integer> actual = storage.getProductsMap();
        assertEquals(expectedMap, actual,
                "If Storage is not empty you should get valid Map");
    }

    @Test
    void process_operationInTransactionIsNull_notOk() {
        Transaction nullOperationTransaction = new Transaction();
        nullOperationTransaction.setOperation(null);
        nullOperationTransaction.setProduct("apple");
        nullOperationTransaction.setQuantity(10);
        List<Transaction> nullOperationInTransaction = validTransactionsList;
        nullOperationInTransaction.add(nullOperationTransaction);
        assertThrows(IllegalArgumentException.class,
                () -> transactionProcessor.process(nullOperationInTransaction, storage),
                "If operation in Transaction is null, exception should be thrown");
    }

    @Test
    void process_listProductIsNull_notOk() {
        Transaction nullProductTransaction = new Transaction();
        nullProductTransaction.setOperation(Transaction.Operation.BALANCE);
        nullProductTransaction.setProduct(null);
        nullProductTransaction.setQuantity(10);
        List<Transaction> nullProductInTransaction = validTransactionsList;
        nullProductInTransaction.add(nullProductTransaction);
        assertThrows(IllegalArgumentException.class,
                () -> transactionProcessor.process(nullProductInTransaction, storage),
                "If Product in Transaction is null, exception should be thrown");
    }
}
