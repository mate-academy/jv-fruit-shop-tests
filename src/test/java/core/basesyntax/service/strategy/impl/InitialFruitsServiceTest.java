package core.basesyntax.service.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InitialFruitsServiceTest {
    private static FruitTransaction fruitTransaction;
    private static InitialFruitsService addition;
    private Map<String, Integer> fruitMap;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction(Operation.BALANCE,
                "banana", 20);
        fruitMap = new HashMap<>();
        addition = new InitialFruitsService();
    }

    @After
    public void tearDown() {
        fruitMap.clear();
    }

    @Test
    public void process_addFruits_ok() {
        addition.process(fruitTransaction, fruitMap);
    }
}
