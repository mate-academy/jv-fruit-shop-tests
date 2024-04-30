package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import db.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitOperationType;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.TransactionProcessorImpl;

class TransactionProcessorImplTest {
    private static final TransactionProcessor transactionProcessor
            = new TransactionProcessorImpl();

    @BeforeEach
    void clearFruitHashMap() {
        Storage.fruitHashMap.clear();
    }

    @Test
    void writeToDataBase_Ok() {
        List<FruitTransaction> fruit = List.of(
                new FruitTransaction(FruitOperationType.BALANCE, "banana", 100),
                new FruitTransaction(FruitOperationType.SUPPLY, "banana", 100),
                new FruitTransaction(FruitOperationType.PURCHASE, "banana", 10),
                new FruitTransaction(FruitOperationType.RETURN, "banana", 5));
        transactionProcessor.processTransactions(fruit);
        Map<String, Integer> expected = Map.of("banana", 195);
        Map<String, Integer> actual = Storage.fruitHashMap;

        assertEquals(expected, actual);
    }

    @Test
    void writeToDataBaseNull_Ok() {
        List<FruitTransaction> fruit = new ArrayList<>();
        transactionProcessor.processTransactions(fruit);
        Map<String, Integer> expected = new HashMap<>();
        Map<String, Integer> actual = Storage.fruitHashMap;

        assertEquals(expected, actual);
    }

    @Test
    void writeToDataNullValue_NotOk() {
        List<FruitTransaction> fruit = List.of(
                new FruitTransaction(FruitOperationType.BALANCE, "banana", 100),
                new FruitTransaction(FruitOperationType.PURCHASE, "banana", 200));

        assertThrows(IllegalArgumentException.class,
                () -> transactionProcessor.processTransactions(fruit));
    }
}
