package core.basesyntax.service.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void setUp() {
        purchaseHandler = new PurchaseHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 3);
        Storage.fruits.put("apple", 23);

    }

    @Test
    public void processOperation_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 20);
        purchaseHandler.processOperation(transaction);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void clear() {
        Storage.fruits.clear();
    }
}
