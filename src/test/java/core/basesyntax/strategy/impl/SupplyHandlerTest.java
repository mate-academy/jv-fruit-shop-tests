package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static SupplyHandler supplyHandler;
    private static FruitTransaction fruitTransaction;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        supplyHandler = new SupplyHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 4);
        expected = new HashMap<>();
    }

    @Test
    public void generalOperation_existFruit_isOk() {
        ShopStorage.fruitsStorage.put("apple", 9);
        expected.put("apple", 13);
        supplyHandler.generalOperation(fruitTransaction);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void generalOperation_notExistFruit_isOk() {
        expected.put("apple", 4);
        supplyHandler.generalOperation(fruitTransaction);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @After
    public void clear() {
        ShopStorage.fruitsStorage.clear();
    }
}
