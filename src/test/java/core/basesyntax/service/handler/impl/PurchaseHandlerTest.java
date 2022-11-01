package core.basesyntax.service.handler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.exception.NoSuchAFruitAtShop;
import core.basesyntax.dao.exception.NotEnoughFruit;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;
    private static FruitTransaction transaction;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        purchaseHandler = new PurchaseHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 3);
        expected = new HashMap<>();
    }

    @Test
    public void processOperation_ExistFruit_Ok() {
        Storage.fruits.put("apple", 23);
        expected.put("apple", 20);
        purchaseHandler.processOperation(transaction);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processOperation_TakeMoreFruitThanIs_NotOk() {
        Storage.fruits.put("apple", 1);
        assertThrows(NotEnoughFruit.class, () ->
                purchaseHandler.processOperation(transaction));
    }

    @Test
    public void processOperation_NonExistFruit_NotOk() {
        assertThrows(NoSuchAFruitAtShop.class, () ->
                purchaseHandler.processOperation(transaction));
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}
