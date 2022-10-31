package core.basesyntax.service.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static ReturnHandler returnHandler;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void setUp() {
        returnHandler = new ReturnHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20);
    }

    @Test
    public void processOperation_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 20);
        returnHandler.processOperation(transaction);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void clear() {
        Storage.fruits.clear();
    }
}
