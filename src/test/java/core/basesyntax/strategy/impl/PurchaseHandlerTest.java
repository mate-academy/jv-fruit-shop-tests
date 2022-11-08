package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;
    private static FruitTransaction fruitTransaction;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        purchaseHandler = new PurchaseHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 3);
        expected = new HashMap<>();
    }

    @Test
    public void generalOperation_existFruit_isOk() {
        ShopStorage.fruitsStorage.put("apple", 23);
        expected.put("apple", 20);
        purchaseHandler.generalOperation(fruitTransaction);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void generalOperation_takeMoreFruitThanIs_notOk() {
        ShopStorage.fruitsStorage.put("apple", 1);
        purchaseHandler.generalOperation(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void generalOperation_nonExistFruit_notOk() {
        purchaseHandler.generalOperation(fruitTransaction);
    }

    @After
    public void clear() {
        ShopStorage.fruitsStorage.clear();
    }
}
