package core.basesyntax.service.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SellFromStorageServiceTest {
    private static FruitTransaction fruitTransaction;
    private static SellFromStorageService sell;
    private Map<String, Integer> fruitMap;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction(Operation.PURCHASE,
                "banana", 10);
        fruitMap = new HashMap<>();
        fruitMap.put("banana", 20);
        sell = new SellFromStorageService();
    }

    @After
    public void tearDown() {
        fruitMap.clear();
    }

    @Test
    public void process_validResult_ok() {
        int expected = 10;
        sell.process(fruitTransaction, fruitMap);
        int actual = fruitMap.get("banana");
        assertEquals(actual, expected);
    }
}
