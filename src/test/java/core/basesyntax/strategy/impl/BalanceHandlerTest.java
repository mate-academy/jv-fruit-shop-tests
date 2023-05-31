package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;
    private static FruitTransaction fruitTransaction;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        balanceHandler = new BalanceHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5);
        expected = new HashMap<>();
    }

    @Test
    public void generalOperation_existFruit_isOk() {
        ShopStorage.fruitsStorage.put("apple", 2);
        expected.put("apple", 7);
        balanceHandler.generalOperation(fruitTransaction);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void generalOperation_nonExistFruit_isOk() {
        expected.put("apple", 5);
        balanceHandler.generalOperation(fruitTransaction);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @After
    public void clear() {
        ShopStorage.fruitsStorage.clear();
    }
}
