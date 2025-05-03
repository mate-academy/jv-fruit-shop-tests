package core.basesyntax.service.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static SupplyHandler supplyHandler;
    private static FruitTransaction transaction;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        supplyHandler = new SupplyHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 4);
        expected = new HashMap<>();
    }

    @Test
    public void processOperation_ExistFruit_Ok() {
        Storage.fruits.put("apple", 9);
        expected.put("apple", 13);
        supplyHandler.processOperation(transaction);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processOperation_NonExistFruit_Ok() {
        expected.put("apple", 4);
        supplyHandler.processOperation(transaction);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}
