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
    private static AdditionToStorageService additionToStorageService;
    private Map<String, Integer> fruitsMap;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction(Operation.SUPPLY,
                "banana", 20);
        fruitsMap = new HashMap<>();
        fruitsMap.put("banana", 10);
        additionToStorageService = new AdditionToStorageService();
    }

    @After
    public void tearDown() {
        fruitsMap.clear();
    }

    @Test
    public void process_validResult_ok() {
        int expected = 30;
        additionToStorageService.process(fruitTransaction, fruitsMap);
        int actual = fruitsMap.get("banana");
        assertEquals(actual, expected);
    }
}
