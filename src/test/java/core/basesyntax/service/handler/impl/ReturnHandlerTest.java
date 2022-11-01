package core.basesyntax.service.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static ReturnHandler returnHandler;
    private static FruitTransaction transaction;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        returnHandler = new ReturnHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 3);
    }

    @Before
    public void init() {
        expected = new HashMap<>();
    }

    @Test
    public void processOperation_ExistFruit_Ok() {
        Storage.fruits.put("apple", 12);
        expected.put("apple", 15);
        returnHandler.processOperation(transaction);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processOperation_NonExistFruit_Ok() {
        expected.put("apple", 3);
        returnHandler.processOperation(transaction);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}
