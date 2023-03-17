package com.mate.fruitshop.strategy;

import static org.junit.Assert.assertEquals;

import com.mate.fruitshop.db.Storage;
import com.mate.fruitshop.model.FruitEntry;
import com.mate.fruitshop.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final FruitEntry STORAGE_ENTRY =
            new FruitEntry("banana", 5);
    private static final int EXPECTED_QUANTITY = 15;
    private static final int FIRST_INDEX = 0;
    private static SupplyHandler supplyHandler;
    private static Transaction supplyTransaction;

    @Before
    public void setUp() {
        supplyHandler = new SupplyHandler();
        supplyTransaction = new Transaction(Transaction.Operation.RETURN,
                "banana", 10);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void process_newFruit_Ok() {
        supplyHandler.process(supplyTransaction);
        assertEquals(supplyTransaction.getFruitName(),
                Storage.fruits.get(FIRST_INDEX).getFruitName());
        assertEquals(supplyTransaction.getQuantity(),
                Storage.fruits.get(FIRST_INDEX).getQuantity());
    }

    @Test
    public void process_existingFruit_Ok() {
        Storage.fruits.add(FIRST_INDEX, STORAGE_ENTRY);
        supplyHandler.process(supplyTransaction);
        assertEquals(EXPECTED_QUANTITY,
                Storage.fruits.get(FIRST_INDEX).getQuantity());
    }
}
