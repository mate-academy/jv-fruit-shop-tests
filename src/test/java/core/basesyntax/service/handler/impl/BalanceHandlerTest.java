package core.basesyntax.service.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;
    private static FruitTransaction transaction;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        balanceHandler = new BalanceHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5);
        expected = new HashMap<>();
    }

    @Test
    public void processOperation_ExistFruit_Ok() {
        Storage.fruits.put("apple", 2);
        expected.put("apple", 7);
        balanceHandler.processOperation(transaction);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processOperation_NonExistFruit_Ok() {
        expected.put("apple", 5);
        balanceHandler.processOperation(transaction);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}
