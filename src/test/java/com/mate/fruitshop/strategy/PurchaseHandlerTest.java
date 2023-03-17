package com.mate.fruitshop.strategy;

import static org.junit.Assert.assertEquals;

import com.mate.fruitshop.db.Storage;
import com.mate.fruitshop.model.FruitEntry;
import com.mate.fruitshop.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final int FIRST_INDEX = 0;
    private static PurchaseHandler purchaseHandler;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseHandler();
        Storage.fruits.add(0, new FruitEntry("banana", 10));
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void process_validPurchase_Ok() {
        FruitEntry storageEntry = Storage.fruits.get(FIRST_INDEX);
        Transaction purchase = new Transaction(Transaction.Operation.PURCHASE,
                storageEntry.getFruitName(), storageEntry.getQuantity());
        purchaseHandler.process(purchase);
        assertEquals(purchase.getFruitName(), storageEntry.getFruitName());
        assertEquals(FIRST_INDEX, storageEntry.getQuantity());
    }

    @Test (expected = RuntimeException.class)
    public void process_purchaseBelowStock_NotOk() {
        FruitEntry storageEntry = Storage.fruits.get(FIRST_INDEX);
        Transaction purchase = new Transaction(Transaction.Operation.PURCHASE,
                storageEntry.getFruitName(), storageEntry.getQuantity() + 1);
        purchaseHandler.process(purchase);
    }
}
