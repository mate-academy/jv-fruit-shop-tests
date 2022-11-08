package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static ReturnHandler returnHandler;
    private static FruitTransaction fruitTransaction;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        returnHandler = new ReturnHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 3);
    }

    @Before
    public void init() {
        expected = new HashMap<>();
    }

    @Test
    public void generalOperation_existFruit_isOk() {
        ShopStorage.fruitsStorage.put("apple", 12);
        expected.put("apple", 15);
        returnHandler.generalOperation(fruitTransaction);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void generalOperation_nonExistFruit_isOk() {
        expected.put("apple", 3);
        returnHandler.generalOperation(fruitTransaction);
        Map<String, Integer> actual = ShopStorage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @After
    public void clear() {
        ShopStorage.fruitsStorage.clear();
    }
}
