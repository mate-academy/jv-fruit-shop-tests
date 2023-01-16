package core.basesyntax.service.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdditionToStorageServiceTest {
    private static FruitTransaction fruitTransaction;
    private static AdditionToStorageService addition;
    private Map<String, Integer> fruitMap;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction(Operation.SUPPLY,
                "banana", 20);
        fruitMap = new HashMap<>();
        fruitMap.put("banana", 10);
        addition = new AdditionToStorageService();
    }

    @After
    public void tearDown() {
        fruitMap.clear();
    }

    @Test
    public void process_validResult_ok() {
        int expected = 30;
        addition.process(fruitTransaction, fruitMap);
        int actual = fruitMap.get("banana");
        assertEquals(actual, expected);
    }
}
