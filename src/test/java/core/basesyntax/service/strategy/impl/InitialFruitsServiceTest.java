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
    private static InitialFruitsService initialFruitsService;
    private Map<String, Integer> fruitsMap;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction(Operation.BALANCE,
                "banana", 20);
        fruitsMap = new HashMap<>();
        initialFruitsService = new InitialFruitsService();
    }

    @After
    public void tearDown() {
        fruitsMap.clear();
    }

    @Test
    public void process_addFruits_ok() {
        initialFruitsService.process(fruitTransaction, fruitsMap);
    }
}
