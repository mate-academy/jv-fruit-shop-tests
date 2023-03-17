package com.mate.fruitshop.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.mate.fruitshop.db.Storage;
import com.mate.fruitshop.model.FruitEntry;
import com.mate.fruitshop.model.Transaction;
import com.mate.fruitshop.service.TransactionsProcessingService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionsProcessingServiceImplTest {
    private static TransactionsProcessingService processor =
            new TransactionsProcessingServiceImpl();
    private static List<Transaction> transactions;
    private static List<FruitEntry> expectedStorage;

    @Before
    public void setUp() {
        transactions = new ArrayList<>();
        transactions.add(new Transaction(Transaction.Operation.BALANCE, "banana", 20));
        transactions.add(new Transaction(Transaction.Operation.BALANCE, "apple", 100));
        transactions.add(new Transaction(Transaction.Operation.SUPPLY, "banana", 100));
        transactions.add(new Transaction(Transaction.Operation.PURCHASE, "banana", 13));
        transactions.add(new Transaction(Transaction.Operation.RETURN, "apple", 10));
        transactions.add(new Transaction(Transaction.Operation.PURCHASE, "apple", 20));
        transactions.add(new Transaction(Transaction.Operation.PURCHASE, "banana", 5));
        transactions.add(new Transaction(Transaction.Operation.SUPPLY, "banana", 50));
        expectedStorage = new ArrayList<>();
        expectedStorage.add(new FruitEntry("banana", 152));
        expectedStorage.add(new FruitEntry("apple", 90));
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void process_EmptyList_Ok() {
        processor.process(new ArrayList<>());
        assertTrue(Storage.fruits.isEmpty());
    }

    @Test
    public void process_MultipleTransactions_Ok() {
        processor.process(transactions);
        for (int i = 0; i < expectedStorage.size(); i++) {
            assertEquals(expectedStorage.get(i).getFruitName(),
                    Storage.fruits.get(i).getFruitName());
            assertEquals(expectedStorage.get(i).getQuantity(),
                    Storage.fruits.get(i).getQuantity());
        }
    }
}
