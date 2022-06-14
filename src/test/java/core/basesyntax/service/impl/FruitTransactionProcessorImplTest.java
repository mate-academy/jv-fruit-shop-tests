package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransactionProcessor;
import core.basesyntax.strategy.FruitAdder;
import core.basesyntax.strategy.FruitHandler;
import core.basesyntax.strategy.FruitSubtractor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitTransactionProcessorImplTest {
    private static Map<String, FruitHandler> handlersMap;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private final FruitTransactionProcessor fruitTransactionProcessor
            = new FruitTransactionProcessorImpl(handlersMap);
    private final List<String> lines = new ArrayList<>();

    @BeforeClass
    public static void beforeAll() {
        handlersMap = new HashMap<>();
        handlersMap.put("b", new FruitAdder());
        handlersMap.put("s", new FruitAdder());
        handlersMap.put("r", new FruitAdder());
        handlersMap.put("p", new FruitSubtractor());
    }

    @Test
    public void transaction_valid_ok() {
        lines.addAll(List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,57",
                "s,banana,50"));
        fruitTransactionProcessor.process(lines);
        Map<String, Integer> expected = Map.of("banana",100, "apple",90);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @Test
    public void transaction_nonValidOperation_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage(
                "Should get exception for inappropriate operation");
        lines.addAll(List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "y,banana,13"));
        fruitTransactionProcessor.process(lines);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
        lines.clear();
    }
}
